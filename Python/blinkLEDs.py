#!/usr/bin/python
import mraa
import time

# Map GPIO block pins to MRAA pin numbers
# Reference: https://learn.sparkfun.com/tutorials/installing-libmraa-on-ubilinux-for-edison

#pins["GP44"] = 31
#pins["GP45"] = 45
#pins["GP46"] = 32
#pins["GP47"] = 46


# Initialize LED controls
leds = {}
leds[0] = mraa.Gpio(9)
leds[1] = mraa.Gpio(10)
leds[2] = mraa.Gpio(11)


ON = 1
OFF = 0
def toggle(led, state):
    led.write(state)

def allLeds(leds, duration, state):
    for l in leds:
        toggle(leds[l], state)

    time.sleep(duration)

def setup():
    # Set direction of LED controls to out
    for color in leds:
        leds[color].dir(mraa.DIR_OUT)

def blink(leds, duration, times):
    for i in range(1, times):
        allLeds(leds, duration, ON)
        allLeds(leds, duration, OFF)

def main():
    setup()
    blink(leds, 0.25, 1000)


if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print "Keyboard interrupt received. Cleaning up..."
        allLeds(leds, 0, OFF)
