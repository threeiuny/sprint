package cn.carey.datasource.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = { "spring.datasource.master.url", "spring.datasource.slave.slave1.url",
        "spring.datasource.slave.slave2.url" })
public class DataSourceConfig {

    // // 使用DataSourceProperties创建数据源--------------------
    // @Bean
    // @ConfigurationProperties(prefix = "spring.datasource.master")
    // public DataSourceProperties masterProperty() {
    // return new DataSourceProperties();
    // }

    // @Bean(name = "masterDataSource")
    // public DataSource masterDataSource(@Qualifier("masterProperty")
    // DataSourceProperties masterProperty) {
    // return masterProperty.initializeDataSourceBuilder().build();
    // }

    // // 使用DataSourceBuilder创建数据源--------------------
    // @Bean(name = "masterDataSource")
    // @ConfigurationProperties(prefix = "spring.datasource.master")
    // public DataSource masterDataSource() {
    // // 配置写数据源
    // return DataSourceBuilder.create().build();
    // }

    // @Bean(name = "slave1DataSource")
    // @ConfigurationProperties(prefix = "spring.datasource.slave.slave1")
    // public DataSource slave1DataSource() {
    // // 配置读数据源1
    // return DataSourceBuilder.create().build();
    // }

    // @Bean(name = "slave2DataSource")
    // @ConfigurationProperties(prefix = "spring.datasource.slave.slave2")
    // public DataSource slave2DataSource() {
    // // 配置读数据源2
    // return DataSourceBuilder.create().build();
    // }

    @Bean(name = "dynamicDataSource")
    public DynamicDataSource dataSource() {
        // 使用 @ConfigurationProperties 加载 master 数据源配置
        DataSource masterDataSource = masterDataSource();
        // 使用 @ConfigurationProperties 加载 slave1 数据源配置
        DataSource slave1DataSource = slave1DataSource();
        // 使用 @ConfigurationProperties 加载 slave2 数据源配置
        DataSource slave2DataSource = slave2DataSource();

        // 配置动态数据源，将主从数据源设置到目标数据源中
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource);
        targetDataSources.put("slave1", slave1DataSource);
        targetDataSources.put("slave2", slave2DataSource);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认数据源为主数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    @ConfigurationProperties(prefix = "spring.datasource.master")
    private DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @ConfigurationProperties(prefix = "spring.datasource.slave.slave1")
    private DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @ConfigurationProperties(prefix = "spring.datasource.slave.slave2")
    private DataSource slave2DataSource() {
        return DataSourceBuilder.create().build();
    }
}
