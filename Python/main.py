#!/usr/bin/python

import os
import time
import cameraRead
import faceDetect
import listen
import moveServo


file = '/usr/lib/edison_config_tools/public/image.png'

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
	    else
	        to_say = "Good, I can eat it all myself."
			cm = 'espeak "'+to_say+'"'
            os.system(cm)

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
		cameraRead.close_camera()
		moveServo.del_servo()
		