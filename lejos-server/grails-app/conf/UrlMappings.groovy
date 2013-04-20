class UrlMappings {

	static mappings = {
//		"/$controller/$action?/$id?"{
//			constraints {
//				// apply constraints here
//			}
//		}

		"/api/$direction/$duration?"(controller:'WebRoverApi', action:[GET:'get'])
		"/api/sense"(controller:'WebRoverApi', action:[GET:'sense'])
		"/api"(controller:'WebRoverApi', action:[POST:'post'], parseRequest:true)
		
		"/"(controller:'WebRoverUi', action:[GET:'html'])
		"500"(view:'/error')
	}
}
