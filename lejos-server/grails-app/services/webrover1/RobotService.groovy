package webrover1

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean

import eass.mas.nxt.NXTBrick
import eass.mas.nxt.BasicRobot
import eass.mas.nxt.RoverUltrasonicSensor
import eass.mas.nxt.RoverTouchSensor
import eass.mas.nxt.RoverSoundSensor
import grails.converters.JSON

import java.io.PrintStream

import lejos.nxt.remote.RemoteMotor
import lejos.robotics.navigation.DifferentialPilot

class RobotService implements InitializingBean, DisposableBean {

	def robot
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
			}
		}
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
		}
	}

    void destroy() throws Exception {
		robot.close()
    }
}
