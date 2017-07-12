package com.yangyang.cloud.admin.relation;

import java.util.List;

/**
 * Created by zhengjh on 2017/7/6.
 */
public class BeanInfo {
    private String bean;
    private List<String> aliases;
    private String type;
    private String resource;

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
