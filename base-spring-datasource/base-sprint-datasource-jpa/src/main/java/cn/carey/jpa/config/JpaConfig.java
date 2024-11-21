package cn.carey.jpa.config;

import cn.carey.jpa.config.properties.JpaProperties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(JpaProperties.class)
@ConditionalOnBean(DataSource.class)
public class JpaConfig {
    
    /**
     * 配置JPA厂商适配器
     * 支持配置:
     * - showSql: 显示SQL语句
     * - generateDdl: 自动生成DDL
     * - databasePlatform: 数据库方言
     * - database: 数据库类型
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(JpaProperties properties) {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(properties.isShowSql());
        adapter.setGenerateDdl(properties.isGenerateDdl());
        adapter.setDatabasePlatform(properties.getDatabasePlatform());
        return adapter;
    }

    /**
     * 配置EntityManagerFactory
     * 支持配置:
     * - packagesToScan: 实体类扫描路径
     * - jpaProperties: Hibernate特定配置
     *   - hibernate.format_sql: 格式化SQL
     *   - hibernate.hbm2ddl.auto: 数据库schema策略
     *   - hibernate.dialect: 数据库方言
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, 
            JpaVendorAdapter jpaVendorAdapter,
            JpaProperties properties) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        factory.setPackagesToScan(properties.getPackagesToScan());
        factory.setJpaPropertyMap(properties.getProperties());
        // 完成属性设置后进行初始化
        factory.afterPropertiesSet();
        return factory;
    }
}
