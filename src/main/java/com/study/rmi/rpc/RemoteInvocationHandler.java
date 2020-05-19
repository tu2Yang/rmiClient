package com.study.rmi.rpc;

import com.study.rmi.rpc.zk.IServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {

    private IServiceDiscovery serviceDiscovery;

    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        String serviceAddress = serviceDiscovery.discovery(request.getClassName());
        TCPTransport tcpTransport = new TCPTransport(serviceAddress);

        return tcpTransport.send(request);
    }

}
