package github.ant.mxsm.common.etcd;

import github.ant.mxsm.common.etcd.exception.EtcdEndPointsExecption;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.Watch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Etcd 客户端
 */

public abstract class EtcdClient {

     protected static final Logger LOGGER = LoggerFactory.getLogger(EtcdClient.class);

     protected static final Charset UTF8 = Charset.forName("UTF-8");

     private Client client;

     public void init(int maxInboundMessageSize, String endpoints){

          LOGGER.info("ETCD endpoints:{},grpc size={}", endpoints,maxInboundMessageSize);

          Optional<String> optional = Optional.ofNullable(endpoints);
          optional.orElseThrow(()->{
               throw new EtcdEndPointsExecption("endpoints 不能为空");
          });

          client = Client.builder().maxInboundMessageSize(maxInboundMessageSize).endpoints(optional.get().split(",")).build();
     }

     public void init(String endpoints){
          LOGGER.info("ETCD endpoints:{}", endpoints);
          Optional<String> optional = Optional.ofNullable(endpoints);
          optional.orElseThrow(()->{
               throw new EtcdEndPointsExecption("endpoints 不能为空");
          });

          client = Client.builder().endpoints(optional.get().split(",")).build();
     }

     public  KV getKVClient(){
          return client.getKVClient();
     }

     public  Lease getLeaseClient(){
          return  client.getLeaseClient();
     }

     public  Watch getWatchClient(){
          return client.getWatchClient();
     }


}
