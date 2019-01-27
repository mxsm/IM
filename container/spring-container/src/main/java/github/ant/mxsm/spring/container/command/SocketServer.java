package github.ant.mxsm.spring.container.command;

import com.datastax.driver.core.*;
import com.google.protobuf.ByteString;
import github.ant.mxsm.protocol.protobuf.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import github.ant.mxsm.netty.socket.server.SocketStarter;
import github.mxsm.zkclient.ZookeeperClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SocketServer implements CommandLineRunner{

	private Cluster cluster = Cluster.builder().addContactPoints("119.23.66.151").build();
	private Session session = cluster.connect("im");
	//@Autowired
	//private ZookeeperClient zkClient;
	private ExecutorService executorService = Executors.newCachedThreadPool();

	public SocketServer() throws UnknownHostException {
	}

	@Override
	public void run(String... args) throws Exception {

		//SocketStarter starter = new SocketStarter(zkClient);
		//starter.start();
        String sql = "INSERT INTO message (messagetime, messageunique, receiver, sender,content) VALUES (?,?,?,?,?)";
		PreparedStatement preparedStatement = session.prepare(sql);
		executorService.execute(()->{
			for(int i = 0; i < 100; ++i){
				Message.MessageProtobuf  messageProtobuf = Message.MessageProtobuf.newBuilder().setPayload(ByteString.copyFromUtf8("lkjsdlkfjsd"+i)).build();
				//SimpleStatement statement = new SimpleStatement(sql,new Date(),UUID.randomUUID(),System.currentTimeMillis()+"","2222222", ByteBuffer.wrap(messageProtobuf.toByteArray()));
				BoundStatement statement = preparedStatement.bind(new Date(),UUID.randomUUID(),System.currentTimeMillis()+"","2222222", ByteBuffer.wrap(messageProtobuf.toByteArray()));
				session.execute(statement);
			}
		});
		//byte[] bytes =session.execute("SELECT * FROM message WHERE messageunique=29d28d20-7355-47bb-896d-3ed3bc3ca3e6 ALLOW FILTERING").one().getBytes("content").array();
		//System.out.println(Message.MessageProtobuf.parseFrom(bytes).getPayload().toStringUtf8());
		//session.close();
		//cluster.close();
	}
	
}
