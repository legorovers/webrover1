webrover1
=========

Code for WebRover1 project, part of International Space Apps Challenge 2013

Project page: http://spaceappschallenge.org/project/webrover1/

## Set up

1. Download Grails 2.2.1 and unzip somewhere suitable
   http://www.grails.org/download

2. Download leJOS and put somewhere suitable
   http://lejos.sourceforge.net/nxj-downloads.php

3. If using Mountain Lion, download old Bluetooth driver and unzip
   http://www.uweschmidt.org/files/WW_MountainLion.zip
   
4. Set up environment variables

export GRAILS_HOME=~/springsource/grails-2.2.1
export NXJ_HOME=~/legorovers/leJOS_NXJ_0.9.1beta-3
export DYLD_LIBRARY_PATH=~/legorovers/WW_MountainLion
export PATH=$PATH:$NXJ_HOME/bin:$GRAILS_HOME/bin
export CLASSPATH=$NXJ_HOME/lib/pc/pccomm.jar:$NXJ_HOME/lib/pc/3rdparty/bluecove.jar
export JAVA_OPTS="-d32 -Dnxj.home=$NXJ_HOME"

5. Install leJOS on NXT brick (this was already done -- or see readme!)

6. Pair Bluetooth
Bluetooth - Set Up Bluetooth Device...
Find it in list
Set Passcode Preferences...
1234

7. Configure robot details in grails-app/conf/Config.groovy

8. Run Grails App

cd lejos-server
grails run-app

(or -Dgrails.env=claudia run-app)

9. Access from browser

http://localhost:8080/api/forward/1000

10. Assumes motors in A and C, sensor in 1 (optional) 


## JSON 

````
{'direction':[left|right|forward|back|stop],
duration:'integer, milliseconds',
distance:'cm',
velocity:integer}
````

## 'Video'

Added Android camera running IP Webcam (address hardwired into HTML page at the moment)
http://lifehacker.com/5650095/ip-webcam-turns-your-android-phone-into-a-remote-camera


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
