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

gServo = servo.ES08A(5)

def move_servo(angle1, angle2, time_interval):
    step_angle = (angle2-angle1)/num_steps
    sleep_time = time_interval/num_steps

    angle = angle1	
    for i in range(1, num_steps):
        gServo.setAngle(angle)
        time.sleep(sleep_time)
        angle += step_angle

    gServo.setAngle(angle2)
    print "Set angle to {0}".format(angle2)
	
def give_candy():
    move_servo(0, 90, 1)
    time.sleep(1)
    move_servo(90, 0, 1)

def del_servo():
# Delete the servo object
    del gServo 