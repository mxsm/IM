package github.ant.mxsm.spring.container.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import github.ant.mxsm.netty.socket.server.SocketStarter;
import github.mxsm.zkclient.ZookeeperClient;

@Component
public class SocketServer implements CommandLineRunner{

	@Autowired
	private ZookeeperClient zkClient;
	
	@Override
	public void run(String... args) throws Exception {
		
		SocketStarter starter = new SocketStarter(zkClient);
		starter.start();
	}

	
	
}
