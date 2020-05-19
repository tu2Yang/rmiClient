package com.study.rmi.rpc.zk;

import com.study.rmi.rpc.zk.loadBalance.LoadBalance;
import com.study.rmi.rpc.zk.loadBalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

public class ServiceDiscoveryImpl implements IServiceDiscovery {

    private String address;

    List<String> repos = new ArrayList<>();

    private CuratorFramework curatorFramework;

    public ServiceDiscoveryImpl(String address) {
        this.address = address;
        // 连接zookeeper
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }

    @Override
    public String discovery(String serviceName) {
        String path = ZkConfig.ZK_REGISTER_PATH + "/" + serviceName;
        try {
            repos = curatorFramework.getChildren().forPath(path);
            // 可以获取子节点（）
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.registerWatcher(path);
        // 接口注册在zk上，如果是集群，则是在某一节点下有多个子节点
        LoadBalance loadBalance = new RandomLoadBalance();

        return loadBalance.selectHost(repos);
    }

    /**
     * 注册监听事件
     * @param path
     */
    private void registerWatcher(String path) {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
