package github.ant.mxsm.common.etcd;

import io.etcd.jetcd.Lease;

import java.util.concurrent.TimeUnit;

public final class LeaseClient extends EtcdClient{

    private Lease lease = getLeaseClient();

    /**
     * 根据ttl获取 leaseId
     * @param ttl
     * @param unit
     * @return
     */
    public long getLeaseId(long ttl, TimeUnit unit){

        //lease.grant(ttl)

        return 0;
    }
}
