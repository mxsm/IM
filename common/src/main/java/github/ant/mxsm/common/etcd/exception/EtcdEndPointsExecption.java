package github.ant.mxsm.common.etcd.exception;

public class EtcdEndPointsExecption extends RuntimeException{

    public EtcdEndPointsExecption() {
    }

    public EtcdEndPointsExecption(String message) {
        super(message);
    }

    public EtcdEndPointsExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public EtcdEndPointsExecption(Throwable cause) {
        super(cause);
    }

    public EtcdEndPointsExecption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
