package webrover1

import org.springframework.beans.factory.DisposableBean
import eass.mas.nxt.NXTBrick
import eass.mas.nxt.BasicRobot
import grails.converters.JSON

import java.io.PrintStream

import lejos.nxt.remote.RemoteMotor
import lejos.robotics.navigation.DifferentialPilot

class RobotService implements DisposableBean {

	def robot
	
	def RobotService() {
		robot = new BasicRobot('claudia', '001653111619') //'0016530F94C9')
		
		if (robot.isConnected()) {
			def brick = robot.getBrick();
			RemoteMotor claudia_motorLeft = brick.getMotorC();
			RemoteMotor claudia_motorRight = brick.getMotorA();
			def pilot = new DifferentialPilot(5, 11, claudia_motorLeft, claudia_motorRight);
			pilot.setTravelSpeed(10);
			pilot.setRotateSpeed(15);
			robot.setPilot(pilot);
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

    void destroy() throws Exception {
		robot.close()
    }
}
