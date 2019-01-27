package github.ant.mxsm.context;

import github.ant.mxsm.protocol.terminal.Terminal;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 通道的上线文通用接口用来给不同的 网络层的协议接入
 */

public interface ChannelContext {

    void writeAndFlush(Object msg);

    void close();

    Terminal getTerminal();

    String remoteAddress();

    <K,V> void put(Pair<K,V> kv);

    <K,V> V get(K key);

}
