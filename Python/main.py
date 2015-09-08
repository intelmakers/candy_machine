#!/usr/bin/python

import cameraRead
import faceDetect

file = 'image.png'
cameraRead.read_image(file)
faceDetect.face_detect(file)




