package github.ant.mxsm.akka;

import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * akka Factory
 * @author mxsm
 *
 */

public class AkkaFactory {

	private static final String DEFAULT_ACTORSYSTEMNAME = "IM";
	
	private static AkkaFactory factory = new AkkaFactory();
	
	private ActorSystem actorSystem;
	
	private String actorSystemName;
	
	private String hostIp;
	
	private AkkaFactory() {
		
	}
	
	public void buildActorSystem(String actorSystemName) {
		
		actorSystem = ActorSystem.create(actorSystemName);
		
	}
	
	public static AkkaFactory getInstance() {
		return factory;
	}
	
	
	
}
