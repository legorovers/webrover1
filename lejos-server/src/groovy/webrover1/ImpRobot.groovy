package webrover1

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.URLENC

class ImpRobot {
	def http
	
	def setup(config) {
		http = new HTTPBuilder( 'http://twitter.com/statuses/' )
	}
	
	def forward() {
		send('1')
	}
	
	def left() {
		send('2')
	}
	
	def right() {
		send('3')
	}
	
	def backward() {
		send('4')
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
	}
	
	def sense(config) {
		[:]
	}
	
	def teardown() {
	}
}
