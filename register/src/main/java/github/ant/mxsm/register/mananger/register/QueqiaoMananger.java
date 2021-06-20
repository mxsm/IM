package github.ant.mxsm.register.mananger.register;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mxsm
 * @Date 2021/6/20
 * @Since 0.1
 */
public class QueqiaoMananger {

    private static final  Logger LOGGER = LoggerFactory.getLogger(QueqiaoMananger.class);

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 鹊桥名称与信息的Map
     */
    private Map<String /*鹊桥名称*/,QueQiaoInfo> queqiaoInfoMap = new HashMap<>(128);

    /**
     * 注册鹊桥
     * @param info
     */
    public void queqiaoRegistry(QueQiaoInfo info){
        QueQiaoInfo queQiaoInfo = queqiaoInfoMap.putIfAbsent(info.getQueqiaoName(), info);
        if(queQiaoInfo != null){
            LOGGER.warn("名称为:{}的鹊桥在鹊桥管理器中存在",info.getQueqiaoName());
        }
    }

    /**
     * 删除鹊桥
     * @param queqiaoName
     */
    public void removeQueqiaoInfo(String queqiaoName){

        try {
            try {
                readWriteLock.writeLock().lock();
                queqiaoInfoMap.remove(queqiaoName);
            } finally {
                readWriteLock.writeLock().unlock();
            }
        } catch (Exception e) {
            LOGGER.error("Remove QueqiaoInfo Error",e);
        }
    }

}
