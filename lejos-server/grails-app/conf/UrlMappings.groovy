class UrlMappings {

	static mappings = {
//		"/$controller/$action?/$id?"{
//			constraints {
//				// apply constraints here
//			}
//		}

		'/api'(controller:'WebRoverApi', action:[GET:'api', POST:'api'], parseRequest:true)

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
