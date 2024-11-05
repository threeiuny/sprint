package cn.carey.datasource.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ConditionalOnBean(name = "dynamicDataSource")
public class TransactionManagerConfig {

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("dynamicDataSource") DataSource dataSource) {
        // 使用 dynamicDataSource 创建事务管理器
        return new DataSourceTransactionManager(dataSource);
    }
}