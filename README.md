Weardrone
=========

## Original idea
As we discussed what we want to build is a Voice command drone 
(so we can execute the order 66 http://starwars.wikia.com/wiki/Order_66)

### Architecture
 - Wearable: listen for voice command ex: (TAKEOFF, LEFT, RIGHT etc)
send this command to the activity that will use the Parse API to push these commands to the cloud
 - Another Application (in the same network as the drone) will receive these Parse command & use the nodeJs client to talk to the drone

### MVP (Minimum Valuable Prototype):
basic navigation command:
TAKEOFF, LAND, LEFT, RIGHT, FWD, BACK, UP, DOWN

### Bonus:
Maybe we can build a camera preview from the Dron or develop Macro!
ex: Macro1: Down, Down-Forward, Forward  (that's a Hadouken btw :)

## Prototype - what was our outcome?
We managed to assemble: 
 - wearable voice commands recognition
 - Parse integration
 - sending commands: wearable -> phone -> Parse cloud -> node.js -> drone
 - building simple sequences of commands with js to control the drone

## Conclusion
Our prototype didn't make it to the demo. We were not able to overcome issues with restarting drone and making it responsive to every command. There were some issues with wearable not picking every command. Also some thing we want to fix in next iteration:
 - STABILITY of the drone 
 - replace Parse, send direct requests, change architecture
 - enable to build command sequences on mobile
 - more logging
 - change wearable voice command to simpler ones, goolge could not pick them many times
