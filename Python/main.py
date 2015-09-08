#!/usr/bin/python

import os
import time
import cameraRead
import faceDetect
import listen
import moveServo
import os.path


file = '/usr/lib/edison_config_tools/public/image.png'
iotivity_dir = '/home/root/events'
iotivity_file = "{0}/candy_PUT.txt".format(iotivity_dir)
my_dir = os.path.dirname(__file__)
candy_audio_file = "{0}/audio/IWantCandylyricsbyAaronCarter.wav".format(my_dir)

def interact():
        to_say = "Nice to meet you. Would you like a candy"
        print to_say
        cm = 'espeak "'+to_say+'"'
        os.system(cm)
        said = listen.doListen()
        print "got words {0}".format(said)
	words = said.split()
	if "YES" in words:
	    to_say = "Do not forget to brush your teeth!!!"
	    cm = 'espeak "'+to_say+'"'
            os.system(cm)
	    moveServo.give_candy()
	else:
	    to_say =  "I O Tivity Party"
            cm = 'espeak "'+to_say+'"'
            os.system(cm)
            time.sleep(0.1)


def iotivity():
    if os.path.exists(iotivity_file):
        os.remove(iotivity_file)
        to_say = "I O Tivity Party"
        cm = 'espeak "'+to_say+'"'
        os.system(cm)
        cm = "aplay {0}".format(candy_audio_file)
        os.system(cm)
        moveServo.give_candy()
        moveServo.give_candy()
        moveServo.give_candy()
            
def main():
        moveServo.init_candy()
	while (1 > 0):
                iotivity()
		cameraRead.read_image(file)
		detected = faceDetect.face_detect(file)

		if (detected != None):
			interact()
                        time.sleep(3)
			
if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print "Keyboard interrupt received. Cleaning up..."
        cameraRead.close_camera()
	moveServo.del_all_servos()
		
