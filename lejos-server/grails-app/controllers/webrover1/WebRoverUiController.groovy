package webrover1

import grails.converters.JSON


class WebRoverUiController {
	def html() {
		def htmlContent = grailsAttributes.getApplicationContext().getResource('index.html').getFile().text
		render text: htmlContent, contentType:'text/html', encoding:'UTF-8'
	}
}
