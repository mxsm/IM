package github.ant.mxsm.spring.container.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import github.ant.mxsm.netty.socket.server.SocketStarter;

@Component
public class SocketServer implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		
		Thread thread = new Thread(new Test());
		thread.start();
	}

	
	class Test implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			SocketStarter starter = new SocketStarter();
			try {
				starter.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
