package com.yangyang.cloud.admin.relation;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过 "http://" + ip + ":" + port + "/beans" 方式来找到提供的服务
 */
@Component
@Scope("prototype")
public class FeignDiscovery implements Runnable {
    protected Logger Log = LoggerFactory.getLogger(getClass());

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private RelateInstance relateInstance;

    @Autowired
    private RestTemplate restTemplate;

    public void setRelateInstance(RelateInstance relateInstance) {
        this.relateInstance = relateInstance;
    }

    @PostConstruct
    public void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public void run() {
        try {
            InstanceInfo instanceInfo = relateInstance.getInstanceInfo();

            String ip = instanceInfo.getIPAddr();
            String port = instanceInfo.getMetadata().get("management.port");
            if (port == null) {
                port = String.valueOf(instanceInfo.getPort());
            }
            String beansUrl = "http://" + ip + ":" + port + "/beans";
            Log.info("get feign beans from {} url={}", instanceInfo.getInstanceId(), beansUrl);

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(beansUrl, String.class);
            String body = responseEntity.getBody();
            Log.debug("contexts: " + body);

            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, ContextInfo.class);
            List<ContextInfo> contextInfoList = objectMapper.readValue(body, javaType);

            List<FeignInfo> feignInfoList = new ArrayList<>();
            for (ContextInfo contextInfo : contextInfoList) {
                List<BeanInfo> beanInfoList = contextInfo.getBeans();

                for (BeanInfo beanInfo : beanInfoList) {
                    List<String> aliases = beanInfo.getAliases();
                    String provider = null;
                    if (aliases != null && aliases.size() > 0) {
                        for (String alias : aliases) {
                            if (alias.endsWith("FeignClient")) {
                                provider = alias.substring(0, alias.length() - 11);
                                break;
                            }
                        }
                    }

                    if (provider != null) {
                        FeignInfo feignInfo = new FeignInfo();
                        feignInfo.setServiceId(provider);
                        feignInfo.setInterfaceName(beanInfo.getType());
                        feignInfoList.add(feignInfo);
                        Log.info("found feign client from {} details: {}", instanceInfo.getInstanceId(), feignInfo);
                    }
                }
            }

            relateInstance.setFeignInfoList(feignInfoList);
        } catch (Exception e) {
            Log.warn("FeignDiscovery fail, instanceId={} {}", relateInstance.getInstanceInfo().getInstanceId(), e.getMessage());
        }
    }
}
