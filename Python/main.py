#!/usr/bin/python

import os
import time
import cameraRead
import faceDetect
import listen


file = '/usr/lib/edison_config_tools/public/image.png'

def interact():
       to_say = "Nice to meet you. Would you like a candy"
       print to_say
       cm = 'espeak "'+to_say+'"'
       os.system(cm)
       words = listen.doListen()
       print "got words {0}".format(words)

def main():
	while (1 > 0):
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