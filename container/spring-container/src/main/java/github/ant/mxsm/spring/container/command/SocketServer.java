package github.ant.mxsm.spring.container.command;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import github.ant.mxsm.netty.socket.server.SocketStarter;

@Component
public class SocketServer implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		
		SocketStarter starter = new SocketStarter();
		starter.start();
		
	}

}
