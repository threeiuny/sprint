package cn.carey.datasource.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * AbstractRoutingDataSource是Spring提供的一个抽象类，继承自AbstractDataSource，实现了接口DataSource。
 * AbstractRoutingDataSource的作用是根据某种策略选择数据源，这个策略是由用户自己来实现的。
 * 通过继承AbstractRoutingDataSource，重写方法determineCurrentLookupKey()，可以实现自己的数据源选择策略。
 * 通过这种方式，可以实现动态数据源的切换。
 */
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
