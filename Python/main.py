#!/usr/bin/python

import cameraRead
import faceDetect
import os

file = 'image.png'
cameraRead.read_image(file)
detected = faceDetect.face_detect(file)

if (detected != None):
	to_say = "Nice to meet you. Would you like a candy"
        cm = 'espeak "'+to_say+'"'
        os.system(cm)





