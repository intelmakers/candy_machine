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


# Parameters for pocketsphinx
LMD   = "/home/root/led-speech-edison/lm/8484.lm"
DICTD = "/home/root/led-speech-edison/lm/8484.dic"
CHUNK = 1024
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 16000
RECORD_SECONDS = 2
PATH = 'output'

LED_ON = 1
LED_OFF = 0
def triggerLeds(words):

    if "RED" in words:
        print "RED"
    if "GREEN" in words:
        print "GREEN"
    if "WHITE" in words:
        print "WHITE"
    if "YELLOW" in words:
        print "YELLOW"
    if "ALL" in words:
        print "ALL"

def decodeSpeech(speech_rec, wav_file):
	wav_file = file(wav_file,'rb')
	wav_file.seek(44)
	speech_rec.decode_raw(wav_file)
	result = speech_rec.get_hyp()
	return result[0]

def main():
    if not os.path.exists(PATH):
        os.makedirs(PATH)

    p = pyaudio.PyAudio()
    speech_rec = ps.Decoder(lm=LMD, dict=DICTD)

    while True:
        # Record audio
    	stream = p.open(format=FORMAT, channels=CHANNELS, rate=RATE, input=True, frames_per_buffer=CHUNK)
    	print("* recording")
    	frames = []
    	for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
    		data = stream.read(CHUNK)
    		frames.append(data)
    	print("* done recording")
    	stream.stop_stream()
    	stream.close()
    	#p.terminate()

        # Write .wav file
        fn = "o.wav"
    	wf = wave.open(os.path.join(PATH, fn), 'wb')
    	wf.setnchannels(CHANNELS)
    	wf.setsampwidth(p.get_sample_size(FORMAT))
    	wf.setframerate(RATE)
    	wf.writeframes(b''.join(frames))
    	wf.close()

        # Decode speech
    	wav_file = os.path.join(PATH, fn)
    	recognised = decodeSpeech(speech_rec, wav_file)
    	rec_words = recognised.split()

        # Trigger LEDs
        print "Trigger"
        triggerLeds(rec_words)

        # Playback recognized word(s)
    	cm = 'espeak "'+recognised+'"'
    	os.system(cm)

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print "Keyboard interrupt received. Cleaning up..."
        allLedsOff(leds)
