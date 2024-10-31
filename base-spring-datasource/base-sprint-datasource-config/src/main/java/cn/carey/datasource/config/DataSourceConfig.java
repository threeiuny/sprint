package cn.carey.datasource.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    // ...已有代码...

    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource() {
        // 配置写数据源
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readDataSource1")
    @ConfigurationProperties(prefix = "spring.datasource.read1")
    public DataSource readDataSource1() {
        // 配置读数据源1
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readDataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.read2")
    public DataSource readDataSource2() {
        // 配置读数据源2
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readDataSource3")
    @ConfigurationProperties(prefix = "spring.datasource.read3")
    public DataSource readDataSource3() {
        // 配置读数据源3
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DynamicDataSource dataSource(
            @Qualifier("writeDataSource") DataSource writeDataSource,
            @Qualifier("readDataSource1") DataSource readDataSource1,
            @Qualifier("readDataSource2") DataSource readDataSource2,
            @Qualifier("readDataSource3") DataSource readDataSource3
    ) {
        // 配置动态数据源，将读写数据源设置到目标数据源中
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("write", writeDataSource);
        targetDataSources.put("read1", readDataSource1);
        targetDataSources.put("read2", readDataSource2);
        targetDataSources.put("read3", readDataSource3);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认数据源为写数据源
        dynamicDataSource.setDefaultTargetDataSource(writeDataSource);
        return dynamicDataSource;
    }

    // ...配置 SqlSessionFactory、事务管理器等...
}
