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


LED_ON = 1
LED_OFF = 0
def toggle(led, state):
    led.write(state)

def allLeds(leds, duration, state):
    for l in leds:
        toggle(l, state)

    time.sleep(duration)

def setup():
    # Set direction of LED controls to out
    for color in leds:
        leds[color].dir(mraa.DIR_OUT)


def main():
    setup()
    while True:
        allLedsOn(leds, 1)
        allLedsOff(leds, 1)

if __name__ == "__main__":
    try:
        main()
    except KeyboardInterrupt:
        print "Keyboard interrupt received. Cleaning up..."
        allLedsOff(leds, 0)

    Status API Training Shop Blog About Pricing 

