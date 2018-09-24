package github.ant.mxsm.akka.actor;

import akka.actor.AbstractActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SimpleClusterListener extends AbstractActor{

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	@Override
	public Receive createReceive() {
		// TODO Auto-generated method stub
		return receiveBuilder()
			      .match(String.class, s -> {
			          log.info("Received String message: {}", s);
			        })
			        .matchAny(o -> log.info("received unknown message"))
			        .build();
	}
	
}
