package com.github.mxsm.register.config;

import com.github.mxsm.common.annotation.NotNull;
import java.util.UUID;

/**
 * @author mxsm
 * @Date 2021/7/4
 * @Since
 */
public class RegisterConfig {

    public static final String REGISTER_DEFAULT_DOMAIN_NAME = "register.im-mxsm.local";

    public static final String REGISTER_DEFAULT_COREDNS_ETCD_PATH = "/im-mxsm";

    private String registerName = UUID.randomUUID().toString();

    private String domainName = REGISTER_DEFAULT_DOMAIN_NAME;

    @NotNull
    private String etcdEndpoints;

    private String coreDNSEtcdPath = REGISTER_DEFAULT_COREDNS_ETCD_PATH;

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getEtcdEndpoints() {
        return etcdEndpoints;
    }

    public String getCoreDNSEtcdPath() {
        return coreDNSEtcdPath;
    }

    public void setCoreDNSEtcdPath(String coreDNSEtcdPath) {
        this.coreDNSEtcdPath = coreDNSEtcdPath;
    }

    public void setEtcdEndpoints(String etcdEndpoints) {
        this.etcdEndpoints = etcdEndpoints;
    }
}
