package com.github.mxsm.etcd;


import static java.nio.charset.StandardCharsets.UTF_8;

import com.github.mxsm.common.Symbol;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.kv.DeleteResponse;
import io.etcd.jetcd.kv.PutResponse;
import io.etcd.jetcd.options.WatchOption;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @date 2022/3/26 10:40
 * @Since 1.0.0
 */
public final class Etcd {

    private static final Logger LOGGER = LoggerFactory.getLogger(Etcd.class);

    private final Client client;

    public Etcd(String endpoints) {
        this.client = Client.builder().endpoints(endpoints.split(Symbol.COMMA)).keepaliveWithoutCalls(true)
            .keepaliveTimeout(Duration.ofSeconds(120)).keepaliveTime(Duration.ofSeconds(120)).build();
    }

    public Client getClient() {
        return this.client;
    }

    public void watch(String key, WatchOption watchOption, Watch.Listener listener) {
        client.getWatchClient().watch(ByteSequence.from(key, UTF_8), watchOption, listener);
    }

    public boolean put(String key, String value) {
        CompletableFuture<PutResponse> respFuture = client.getKVClient()
            .put(ByteSequence.from(key, UTF_8), ByteSequence.from(value, UTF_8));
        try {
            respFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            //e.printStackTrace();
            LOGGER.error("Put key=" + key + ",value=" + value + " to etcd error", e);
            return false;
        }
        return true;
    }

    public boolean delete(String key) {
        CompletableFuture<DeleteResponse> respFuture = client.getKVClient().delete(ByteSequence.from(key, UTF_8));
        try {
            respFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            //e.printStackTrace();
            LOGGER.error("Delete key=" + key + " from etcd error", e);
            return false;
        }
        return true;
    }
}
