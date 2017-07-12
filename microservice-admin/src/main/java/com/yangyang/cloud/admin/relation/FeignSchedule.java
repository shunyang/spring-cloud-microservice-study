package com.yangyang.cloud.admin.relation;

import com.netflix.appinfo.InstanceInfo;
import com.yangyang.cloud.admin.SpringBootAdminApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 每隔30s调度一次，获取服务的消费者
 */
@Component
@Configurable
@EnableScheduling
public class FeignSchedule {
    protected Logger Log = LoggerFactory.getLogger(getClass());

    private long lastRunTimestamp = 0L;
    /**
     * serviceId -> (instanceUrl -> instance)
     */
    private Map<String, Map<String, RelateInstance>> dataMap = new ConcurrentHashMap<>();

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    private EurekaDiscoveryClient discoveryClient;

    @Scheduled(fixedRate = 30_000)
    public void run() {
        long currentRunTimestamp = System.currentTimeMillis();

        List<RelateInstance> dirtyRelateInstanceList = loadChangeInstances();

        for (RelateInstance relateInstanceInfo : dirtyRelateInstanceList) {
            FeignDiscovery feignDiscovery = SpringBootAdminApplication.APPLICATION_CONTEXT.getBean(FeignDiscovery.class);
            feignDiscovery.setRelateInstance(relateInstanceInfo);
            Log.info("found dirty instance: {}", relateInstanceInfo.getInstanceInfo().getInstanceId());
            cachedThreadPool.submit(feignDiscovery);
        }

        lastRunTimestamp = currentRunTimestamp;
    }

    private List<RelateInstance> loadChangeInstances() {
        List<RelateInstance> dirtyRelateInstanceList = new ArrayList<>();

        List<String> serviceList = discoveryClient.getServices();

        // 首先移除已下线的service
        Set<String> serviceSet = new HashSet<>(serviceList);
        Iterator<Map.Entry<String, Map<String, RelateInstance>>> it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Map<String, RelateInstance>> entry = it.next();
            if (!serviceSet.contains(entry.getKey())) {
                it.remove();
            }
        }

        for (String serviceId : serviceList) {
            Map<String, RelateInstance> instanceInfoMap = dataMap.get(serviceId);
            if (instanceInfoMap == null) {
                instanceInfoMap = new HashMap<>();
                dataMap.put(serviceId, instanceInfoMap);
            }

            Set<String> aliveInstanceIdSet = new HashSet<>();
            List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceId);
            for (ServiceInstance serviceInstance : serviceInstanceList) {
                InstanceInfo instanceInfo = ((EurekaDiscoveryClient.EurekaServiceInstance) serviceInstance).getInstanceInfo();
                aliveInstanceIdSet.add(instanceInfo.getInstanceId());

                RelateInstance relateInstanceInfo = instanceInfoMap.get(instanceInfo.getInstanceId());
                if (relateInstanceInfo == null) {
                    relateInstanceInfo = new RelateInstance();
                    instanceInfoMap.put(instanceInfo.getInstanceId(), relateInstanceInfo);
                }
                relateInstanceInfo.setInstanceInfo(instanceInfo);

                if (instanceInfo.getLastDirtyTimestamp() > lastRunTimestamp - 120_000) {
                    dirtyRelateInstanceList.add(relateInstanceInfo);
                }
            }

            Iterator<Map.Entry<String, RelateInstance>> itt = instanceInfoMap.entrySet().iterator();
            while (itt.hasNext()) {
                Map.Entry<String, RelateInstance> entry = itt.next();
                if (!aliveInstanceIdSet.contains(entry.getKey())) {
                    itt.remove();
                }
            }
        }

        return dirtyRelateInstanceList;
    }

    public Map<String, Map<String, RelateInstance>> getDataMap() {
        return dataMap;
    }
}

