package webrover1

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean

import grails.converters.JSON

import java.io.PrintStream
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class RobotService implements InitializingBean, DisposableBean {

	def robot
	def commands
	def delay = 0
	def running = true
	def grailsApplication

    public void afterPropertiesSet() throws Exception {
		def config = grailsApplication.config.nxt.robot
		robot = config.type == 'imp' ? new ImpRobot() : new NXTRobot()
		robot.setup(config)
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
							robot.forward()
							break
						case 'left':
							robot.left()
							break
						case 'right':
							robot.right()
							break
						case 'backward':
							robot.backward()
							break
						case 'stop':
							duration = 0
							break
					}
					
					while (duration > 0 && commands.size() == 0) {
						Thread.sleep(10)
						duration -= 10

						def obstacle = false
		    			if (config.sensor.equals('ultrasonic')) {
		    				def distance = ((RoverUltrasonicSensor) robot.getSensor(1)).distance()
		    				println("$distance")
		    				if (distance < 50) {
		    				    obstacle = true
		    				}
						} else if (config.sensor.equals('touch')) {
							def bump = ((RoverTouchSensor) robot.getSensor(1)).isPressed()
						} else if (config.sensor.equals('sound')) {
						    def value = ((RoverSoundSensor) robot.getSensor(1)).readValue()
						} else if (config.sensor.equals('light')) {
							def value = ((RoverLightSensor) robot.getSensor(1)).getLightValue();
						}
						
						if (obstacle) {
							duration = 0
							println("$duration")
						}

					}
					if (commands.size() == 0) {
						robot.stop()
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
		return robot.sense(config)
	}

    void destroy() throws Exception {
		running = false
		robot.teardown()
    }
}
