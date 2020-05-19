package com.study.rmi.rpc;

import com.study.rmi.rpc.zk.IServiceDiscovery;

import java.lang.reflect.Proxy;

public class RpcClientProxy {

    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T clientProxy(final Class<T> interfaceCls) {

        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(), new Class[]{interfaceCls},
                new RemoteInvocationHandler(serviceDiscovery));
    }

}
