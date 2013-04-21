package webrover1

import grails.converters.JSON


class WebRoverUiController {
	
	def cameraService
	
	def html() {
		def htmlContent = grailsAttributes.getApplicationContext().getResource('index.html').getFile().text
		render text:htmlContent, contentType:'text/html', encoding:'UTF-8'
	}
	
	def snapshot() {
		cameraService.snapshot()
		render cameraService.frame
	}
	
	def video() {
		response.setContentType('image/jpeg')
		cameraService.image().withInputStream { response.outputStream << it }
	}
}
