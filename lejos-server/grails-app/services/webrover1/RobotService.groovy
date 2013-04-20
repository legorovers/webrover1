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

import lejos.nxt.remote.RemoteMotor
import lejos.robotics.navigation.DifferentialPilot

class RobotService implements InitializingBean, DisposableBean {

	def robot
	def commands
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
		
		commands = new ArrayBlockingQueue(1)
		def th = Thread.start {
			while (running) {
				println 'active'
				def recent = []
				commands.drainTo(recent)
				println recent
				
				if (recent.size()) {
					def result = recent[0]
					def direction = result[0]
					def duration = result[1]
					switch (direction) {
						case 'forward':
							forward(duration)
							break
						case 'left':
							left(duration)
							break
						case 'right':
							right(duration)
							break
						case 'backward':
							backward(duration)
							break
						case 'stop':
							stop()
							break
					}
				}
				Thread.sleep(100)
			}
		}
		
	}
	
	def action(direction, duration) {
		println direction
		println duration
		commands.put([direction, duration])
	}
	
    def left(duration) {
		robot.pilot.rotateLeft()
		Thread.sleep(duration)
		robot.pilot.stop()
    }
	def right(duration) {
		robot.pilot.rotateRight()
		Thread.sleep(duration)
		robot.pilot.stop()
	}
	def forward(duration) {
		robot.pilot.forward()
		Thread.sleep(duration)
		robot.pilot.stop()
	}
	def backward(duration) {
		robot.pilot.backward()
		Thread.sleep(duration)
		robot.pilot.stop()
	}
	def stop() {
		robot.pilot.stop()
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
