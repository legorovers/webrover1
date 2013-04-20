class UrlMappings {

	static mappings = {
//		"/$controller/$action?/$id?"{
//			constraints {
//				// apply constraints here
//			}
//		}

		"/api/$direction?/$duration?"(controller:'WebRoverApi', action:[GET:'command', POST:'command'], parseRequest:true)
		"/api/sense"(controller:'WebRoverApi', action:[GET:'sense'])
		"/api/delay/$delay?"(controller:'WebRoverApi', action:[GET:'delay'])
		
		"/"(controller:'WebRoverUi', action:[GET:'html'])
		"/snapshot"(controller:'WebRoverUi', action:[GET:'snapshot'])
		"/video.jpg"(controller:'WebRoverUi', action:[GET:'video'])
		"500"(view:'/error')
	}
}
