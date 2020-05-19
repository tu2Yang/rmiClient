package com.study.rmi.rpc.zk.loadBalance;

import java.util.List;

public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String selectHost(List<String> repos) {
        if (null == repos || repos.size() == 0) {
            return null;
        }
        if(repos.size() == 1) {
            return repos.get(0);
        }
        return doSelect(repos);
    }

    protected abstract String doSelect(List<String> repos);

}
