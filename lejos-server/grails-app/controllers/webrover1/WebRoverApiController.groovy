package webrover1

import grails.converters.JSON
import groovy.util.logging.Log4j

@Log4j
class WebRoverApiController {

	def robotService
	
    def get() {
		action(params.direction, params.duration as int)
		def result = [result:'OK']
		render result as JSON
	}

    def post() {
//		def json = request.JSON
//		println json
		println params.direction
		println params.duration
		action(params.direction, params.duration as int)
		def result = [result:'OK']
		render result as JSON
	}
	
	def sense() {
		def result = [distance:robotService.sense()]
		render result as JSON
	}
	
	protected action(direction, duration) {
		switch (direction) {
			case 'forward':
				robotService.forward(duration)
				break
			case 'left':
				robotService.left(duration)
				break
			case 'right':
				robotService.right(duration)
				break
			case 'backward':
				robotService.backward(duration)
				break
			case 'stop':
				robotService.stop()
				break
		}
	}
}
