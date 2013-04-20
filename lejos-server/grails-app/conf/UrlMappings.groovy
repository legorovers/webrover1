class UrlMappings {

	static mappings = {
//		"/$controller/$action?/$id?"{
//			constraints {
//				// apply constraints here
//			}
//		}

		"/api/$direction/$duration?"(controller:'WebRoverApi', action:[GET:'get'])
		"/api"(controller:'WebRoverApi', action:[POST:'post'], parseRequest:true)
		
		"/"(view:"/index")
		"500"(view:'/error')
	}
}
