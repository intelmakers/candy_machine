#!/usr/bin/python
import numpy
import cv2
import sys
from espeak import espeak


def face_detect(in_file):
	img = cv2.imread(infile)
	out_file = "{0}.detected".format(in_file)
	gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)

	faceCascade = cv2.CascadeClassifier('/usr/lib/edison_config_tools/public/haarcascade_frontalface_alt.xml')
	faces = faceCascade.detectMultiScale(gray,scaleFactor=1.1,minNeighbors=5, minSize=(30, 30), flags = cv2.cv.CV_HAAR_SCALE_IMAGE) 

	num_of_faces = len(faces)
	print 'Num Faces', num_of_faces
	
	if num_of_faces < 1:
		return None
		
	if num_of_faces > 1:
		espeak.synth("Too many people. One at a time please")
		return None
		
	for (x,y,w,h) in faces:
	  cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)

	cv2.imwrite(out_file,img)
	return face[0];

