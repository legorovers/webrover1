package webrover1

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean

class CameraService implements InitializingBean, DisposableBean {
	
	def fps = 2
	def delay = 1
	def frame = 0
	def running = true
	
    public void afterPropertiesSet() throws Exception {
		def th = Thread.start {
			while (running) {
				snapshot()
				Thread.sleep(1000/fps as int)
			}
		}
	}
	
    def snapshot() {
		def next = (frame + 1) % (delay + 1)
		new File("frame-${next}.jpeg").withOutputStream { out ->
			new URL('http://172.17.5.198:8080/shot.jpg').withInputStream { out << it }
		}
		frame = next
    }
	
	def image() {
		new File("frame-${frame}.jpeg")
	}

    void destroy() throws Exception {
		running = false
    }
}
