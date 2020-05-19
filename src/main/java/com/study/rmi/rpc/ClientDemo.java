package com.study.rmi.rpc;

import com.study.rmi.rpc.zk.IServiceDiscovery;
import com.study.rmi.rpc.zk.ServiceDiscoveryImpl;
import com.study.rmi.rpc.zk.ZkConfig;

public class ClientDemo {

    public static void main(String[] args) {

        IServiceDiscovery serviceDiscovery = new ServiceDiscoveryImpl(ZkConfig.CONNECTION_STR);

        RpcClientProxy rpcClientProxy = new RpcClientProxy(serviceDiscovery);
        IStudyHello iStudyHello = rpcClientProxy.clientProxy(IStudyHello.class);

        System.out.println(iStudyHello.sayHello("牛逼啊"));
    }

}
