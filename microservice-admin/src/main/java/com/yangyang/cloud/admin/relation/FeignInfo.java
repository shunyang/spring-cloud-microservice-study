package com.yangyang.cloud.admin.relation;

/**
 * Created by zhengjh on 2017/7/6.
 */
public class FeignInfo {
    private String interfaceName;
    private String serviceId;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"interfaceName\":\"")
                .append(interfaceName).append('\"');
        sb.append(",\"serviceId\":\"")
                .append(serviceId).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
