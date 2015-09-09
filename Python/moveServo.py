# Author: John Van Drasek <john.r.van.drasek@intel.com> 
# Copyright (c) 2015 Intel Corporation.
#
# Permission is hereby granted, free of charge, to any person obtaining
# a copy of this software and associated documentation files (the
# "Software"), to deal in the Software without restriction, including
# without limitation the rights to use, copy, modify, merge, publish,
# distribute, sublicense, and/or sell copies of the Software, and to
# permit persons to whom the Software is furnished to do so, subject to
# the following conditions:
#
# The above copyright notice and this permission notice shall be
# included in all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
# EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
# NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
# LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
# OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
# WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

import time
import pyupm_servo as servo 

num_steps = 20

candyPin = 5
leftPin = 3
rightPin = 6
middlePin = 9

cServo = None
left = None
right = None
middle = None

def init_candy():
    global cServo
    cServo = servo.ES08A(candyPin)

def init_spider():
    global left
    global right
    global middle
    left = servo.ES08A(leftPin)
    right = servo.ES08A(rightPin)
    middle = servo.ES08A(middlePin)

    left.setAngle(90)
    right.setAngle(90)
    middle.setAngle(90)


def move_servo(aServo, angle1, angle2, time_interval):
    step_angle = (angle2-angle1)/num_steps
    sleep_time = time_interval/num_steps

    angle = angle1	
    for i in range(1, num_steps):
        aServo.setAngle(angle)
        time.sleep(sleep_time)
        angle += step_angle

    aServo.setAngle(angle2)
    print "Set angle to {0}".format(angle2)
	

def give_candy():
    move_servo(cServo, 0, 90, 1)
    time.sleep(1)
    move_servo(cServo, 90, 0, 1)


def step_left():
    middle.setAngle(75);
    for pos in range(140, 40, -4):
        left.setAngle(pos);
        right.setAngle(pos);
        time.sleep(0.02);

def step_right():
    middle.setAngle(105);
    for pos in range(40, 140, 4):
        left.setAngle(pos);
        right.setAngle(pos);
        time.sleep(0.02);

def go_steps(steps_to_go):
    for i in range(1, steps_to_go):
        step_right()
        step_left()


def del_servo(aServo):
    # Delete the servo object
    if aServo:      # del only if not None
        del aServo 


def del_all_servos():
    del_servo(cServo)
    del_servo(left)
    del_servo(right)
    del_servo(middle)

