package com.yangyang.cloud.admin.relation;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhengjh on 2017/7/6.
 */
public class InterfaceInfo {
    private String name;
    private Set<String> consumers;
    private Set<String> providers;

    public InterfaceInfo(String name) {
        this.name = name;
        consumers = new HashSet<>();
        providers = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getConsumers() {
        return consumers;
    }

    public void setConsumers(Set<String> consumers) {
        this.consumers = consumers;
    }

    public Set<String> getProviders() {
        return providers;
    }

    public void setProviders(Set<String> providers) {
        this.providers = providers;
    }
}
