webrover1
=========

Code for WebRover1 project, part of International Space Apps Challenge 2013

Project page: http://spaceappschallenge.org/project/webrover1/

## JSON 

````
{'direction':[left|right|forward|back|stop],
duration:'integer, milliseconds',
distance:'cm',
velocity:integer}
````

## UI Sketches

### Main drive screen
1. Web cam feed at the top (if we do a webcam feed)
2. D-Pad controller touch area below. User swipes (or click/touch) to tell robot which direction to drive in
3. Underneath drive controls, set command delay in seconds
4. Under that, buttons to set other options and build your own autonomous rules
![Main drive screen](https://pbs.twimg.com/media/BITfLrlCEAMjBGX.jpg)

### Rule builder
Easy rule builder, adapting Tiago Jesus original desktop interface for smaller, mobile devices https://github.com/tiagojesus/LegoRoversUI

![Rule builder](https://pbs.twimg.com/media/BITi_QnCUAIuRyi.jpg:large)
