package github.ant.mxsm.akka;

import akka.actor.ActorSystem;
import akka.actor.Props;
import github.ant.mxsm.akka.actor.SimpleClusterListener;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception{
    	ActorSystem system = ActorSystem.create("ClusterSystem");
    	 system.actorOf(Props.create(SimpleClusterListener.class),
    	          "clusterListener");
    }
}
