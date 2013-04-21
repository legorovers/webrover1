package webrover1

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean

import eass.mas.nxt.NXTBrick
import eass.mas.nxt.BasicRobot
import eass.mas.nxt.RoverUltrasonicSensor
import eass.mas.nxt.RoverTouchSensor
import eass.mas.nxt.RoverSoundSensor
import eass.mas.nxt.RoverLightSensor
import grails.converters.JSON

import java.io.PrintStream
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import lejos.nxt.remote.RemoteMotor
import lejos.robotics.navigation.DifferentialPilot

class RobotService implements InitializingBean, DisposableBean {

	def robot
	def commands
	def delay = 0
	def running = true
	def grailsApplication

    public void afterPropertiesSet() throws Exception {
		def config = grailsApplication.config.nxt.robot
		robot = new BasicRobot(config.name, config.address)
		
		if (robot.isConnected()) {
		    /* Robot set up */
			def brick = robot.getBrick();
			def claudia_motorLeft = brick.getMotorC();
			def claudia_motorRight = brick.getMotorA();
			def pilot = new DifferentialPilot(5, 11, claudia_motorLeft, claudia_motorRight);
			pilot.setTravelSpeed(10);
			pilot.setRotateSpeed(15);
			robot.setPilot(pilot);
			
			if (config.sensor.equals('ultrasonic')) {
				RoverUltrasonicSensor uSensor = new RoverUltrasonicSensor(brick, 1);
				robot.setSensor(1, uSensor);
			} else if (config.sensor.equals('touch')) {
			    RoverTouchSensor tSensor = new RoverTouchSensor(brick, 1);
			    robot.setSensor(1, tSensor);
			} else if (config.sensor.equals('sound')) {
			 	RoverSoundSensor sSensor = new RoverSoundSensor(brick, 1);
			 	robot.setSensor(1, sSensor);
			} else if (config.sensor.equals('light')) {
				RoverLightSensor lSensor = new RoverLightSensor(brick, 1);
				robot.setSensor(1, lSensor);
			}
		}
		delayThread = Executors.newScheduledThreadPool(1)
		commands = new ArrayBlockingQueue(100)
		def th = Thread.start {
			while (running) {
				def recent = []
				commands.drainTo(recent)
				
				if (recent.size()) {
					println recent.size()
					def command = recent[-1]
					println command.direction
					def duration = command.duration
					println duration
					switch (command.direction) {
						case 'forward':
							robot.pilot.forward()
							break
						case 'left':
							robot.pilot.rotateLeft()
							break
						case 'right':
							robot.pilot.rotateRight()
							break
						case 'backward':
							robot.pilot.backward()
							break
						case 'stop':
							duration = 0
							//robot.pilot.stop()
							break
					}
					while (duration > 0 && commands.size() == 0) {
						Thread.sleep(10)
						duration -= 10
					}
					if (commands.size() == 0) {
						robot.pilot.stop()
					}
				}
				if (commands.size() == 0) {
					Thread.sleep(100)
				}
			}
		}
		
	}
	
	def delayThread 
	def action(direction, duration) {
		delayThread.schedule({
			commands.put([direction:direction, duration:duration])
		} as Runnable, delay, TimeUnit.MILLISECONDS)
	}
	
	def sense() {
		def config = grailsApplication.config.nxt.robot
	
	    int sensornumber = 1;
	    if (config.sensor.equals('ultrasonic')) {
	    	def distance = ((RoverUltrasonicSensor) robot.getSensor(1)).distance()
			return [distance:distance]
		} else if (config.sensor.equals('touch')) {
			def bump = ((RoverTouchSensor) robot.getSensor(1)).isPressed()
			return [pressed:bump]
		} else if (config.sensor.equals('sound')) {
		    def value = ((RoverSoundSensor) robot.getSensor(1)).readValue()
		    return [sound:value]
		} else if (config.sensor.equals('light')) {
			def value = ((RoverLightSensor) robot.getSensor(1)).getLightValue();
			return [light:value]
		}
	}

    void destroy() throws Exception {
		running = false
		robot.close()
    }
}
