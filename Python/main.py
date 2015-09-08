#!/usr/bin/python

import cameraRead
import faceDetect
import os
import time

file = '/usr/lib/edison_config_tools/public/image.png'

while (1 > 0):
	cameraRead.read_image(file)
	detected = faceDetect.face_detect(file)

	if (detected != None):
		interact()
		time.sleep(30)
		
	

def interact():
	to_say = "Nice to meet you. Would you like a candy"
    cm = 'espeak "'+to_say+'"'
    os.system(cm)




