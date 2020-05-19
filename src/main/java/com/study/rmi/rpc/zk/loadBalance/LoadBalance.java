package com.study.rmi.rpc.zk.loadBalance;

import java.util.List;

public interface LoadBalance {

    String selectHost(List<String> repos);

}
