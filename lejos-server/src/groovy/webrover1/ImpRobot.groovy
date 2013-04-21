package webrover1

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.URLENC

class ImpRobot {
	def http
	
	def setup(config) {
		http = new HTTPBuilder( 'http://twitter.com/statuses/' )
	}
	
	def forward() {
		send('5')
	}
	
	def left() {
		send('6')
	}
	
	def right() {
		send('7')
	}
	
	def backward() {
		send('8')
	}
	
	def send(value) {
		println "sending $value"
		def postBody = [value:value] // will be url-encoded
		http.post(path:'https://api.electricimp.com/v1/95e71d3229a2fb1f/303e379a32c7c7af', body:postBody, requestContentType:URLENC ) { resp ->
		   println resp
		}
		println "done"
	}
	
	def stop() {
		send('9')
	}
	
	def sense(config) {
		[:]
	}
	
	def distance() {
		255
	}
	
	def pressed() {
		false
	}

	def teardown() {
	}
}
