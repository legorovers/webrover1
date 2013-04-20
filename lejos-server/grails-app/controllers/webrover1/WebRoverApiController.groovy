package webrover1

import grails.converters.JSON


class WebRoverApiController {

	def robotService
	
    def api() {
		def json = request.JSON
		json.direction
		switch (json.direction) {
			case 'forward':
				robotService.forward(json.duration)
				break
			case 'left':
				robotService.left(json.duration)
				break
			case 'right':
				robotService.right(json.duration)
				break
			case 'backward':
				robotService.backward(json.duration)
				break
			case 'stop':
				robotService.stop(json.duration)
				break
		}
		def result = [result:'OK']
		render result as JSON
	}
}
