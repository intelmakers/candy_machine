#!/usr/bin/python
import collections
import mraa
import os
import sys
import time

# Import things for pocketsphinx
import pyaudio
import wave
import pocketsphinx as ps
import sphinxbase

my_dir = os.path.dirname(__file__)
dict_name = 8670

# Parameters for pocketsphinx
LMD   = "{0}/dict/{1}.lm".format(my_dir, dict_name)
DICTD = "{0}/dict/{1}.dic".format(my_dir, dict_name)
CHUNK = 1024
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 16000
RECORD_SECONDS = 2
PATH = 'output'

//   if "ALL" in words:
//        print "ALL"

if not os.path.exists(PATH):
    os.makedirs(PATH)

pya = pyaudio.PyAudio()
speech_rec = ps.Decoder(lm=LMD, dict=DICTD)

def decodeSpeech(speech_rec, wav_file):
	wav_file = file(wav_file,'rb')
	wav_file.seek(44)
	speech_rec.decode_raw(wav_file)
	result = speech_rec.get_hyp()
	return result[0]

	
def doListen():
	# Record audio
	stream = pya.open(format=FORMAT, channels=CHANNELS, rate=RATE, input=True, frames_per_buffer=CHUNK)
	print("* recording")
	frames = []
	for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
		data = stream.read(CHUNK)
		frames.append(data)
	print("* done recording")
	stream.stop_stream()
	stream.close()
	#pya.terminate()

	# Write .wav file
	fn = "o.wav"
	wf = wave.open(os.path.join(PATH, fn), 'wb')
	wf.setnchannels(CHANNELS)
	wf.setsampwidth(pya.get_sample_size(FORMAT))
	wf.setframerate(RATE)
	wf.writeframes(b''.join(frames))
	wf.close()

	# Decode speech
	wav_file = os.path.join(PATH, fn)
	recognised = decodeSpeech(speech_rec, wav_file)
	rec_words = recognised.split()

	print "Recognized: {0}".format(recognised)

	# Playback recognized word(s)
	cm = 'espeak "'+recognised+'"'
	os.system(cm)
	return recognised


