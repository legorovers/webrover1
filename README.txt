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

7. Run Grails App

cd lejos-server
grails run-app

8. Access from browser

http://localhost:8080/api/forward/1000
