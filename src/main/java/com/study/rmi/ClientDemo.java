package com.study.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDemo {

    public static void main(String[] args) {
        try {
            IHelloService helloService = (IHelloService) Naming.lookup("rmi://127.0.0.1/Hello");
            System.out.println(helloService.sayHello("niubi"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
