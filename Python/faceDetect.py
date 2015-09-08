#!/usr/bin/python
import numpy
import cv2
import sys

file = "/home/root/test_image.png"
out_file = "/home/root/test_face.png"

img = cv2.imread(file)
gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)

faceCascade = cv2.CascadeClassifier('/usr/lib/edison_config_tools/public/haarcascade_frontalface_alt.xml')
faces = faceCascade.detectMultiScale(gray,scaleFactor=1.1,minNeighbors=5, minSize=(30, 30), flags = cv2.cv.CV_HAAR_SCALE_IMAGE)
print 'Num Faces', len(faces)
for (x,y,w,h) in faces:
  cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)

cv2.imwrite(out_file,img)

