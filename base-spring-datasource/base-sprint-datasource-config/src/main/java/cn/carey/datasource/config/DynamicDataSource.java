package cn.carey.datasource.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final List<Object> READ_DATASOURCE_KEYS = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = DynamicDataSourceContextHolder.getDataSourceKey();
        if ("slave".equals(dataSourceKey) && !READ_DATASOURCE_KEYS.isEmpty()) {
            // 从数据源实现简单的轮询负载均衡
            int index = counter.getAndIncrement() % READ_DATASOURCE_KEYS.size();
            return READ_DATASOURCE_KEYS.get(index);
        }
        // 默认使用写数据源
        return "master";
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        // 初始化从数据源键列表
        for (Object key : super.getResolvedDataSources().keySet()) {
            if (key.toString().startsWith("slave")) {
                READ_DATASOURCE_KEYS.add(key);
            }
        }
    }
}
