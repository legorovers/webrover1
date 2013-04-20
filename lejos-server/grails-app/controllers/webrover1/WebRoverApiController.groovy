package webrover1

import grails.converters.JSON


class WebRoverApiController {

	def robotService
	
    def get() {
		action(params.direction, params.duration as int)
		def result = [result:'OK']
		render result as JSON
	}

    def post() {
		def json = request.JSON
		action(json.direction, json.duration)
		def result = [result:'OK']
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
