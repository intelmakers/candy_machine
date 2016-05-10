#!/usr/bin/python

import os
import time
import moveServo
import os.path
from multiprocessing import Process


my_dir = os.path.dirname(__file__)

            
def main():
    moveServo.init_candy()
    moveServo.move_servo_ext(0, 180, 25)
    print "sleep 5"
#    time.sleep(1)
    moveServo.move_servo_ext(180, 90, 25)
    print "sleep 5"
 #   time.sleep(1)
    moveServo.move_servo_ext(90, 0, 25)
			
if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print "Keyboard interrupt received. Cleaning up..."
	moveServo.del_all_servos()
        print "Keyboard interrupt Clean up done"
		
