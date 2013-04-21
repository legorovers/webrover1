package webrover1

import eass.mas.nxt.NXTBrick
import eass.mas.nxt.BasicRobot
import eass.mas.nxt.RoverUltrasonicSensor
import eass.mas.nxt.RoverTouchSensor
import eass.mas.nxt.RoverSoundSensor
import eass.mas.nxt.RoverLightSensor

import lejos.nxt.remote.RemoteMotor
import lejos.robotics.navigation.DifferentialPilot

class NXTRobot {
	def robot
	
	def setup(config) {
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
	}
	
	def forward() {
		robot.pilot.forward()
	}
	
	def left() {
		robot.pilot.rotateLeft()
	}
	
	def right() {
		robot.pilot.rotateRight()
	}
	
	def backward() {
		robot.pilot.backward()
	}
	
	def stop() {
		robot.pilot.stop()
	}
	
	def sense(config) {
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
	
	def teardown() {
		robot.close()		
	}
}
