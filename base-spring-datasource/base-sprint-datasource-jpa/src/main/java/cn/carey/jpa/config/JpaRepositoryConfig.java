package cn.carey.jpa.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

/**
 * JPA Repositories配置类
 * 用于启用JPA Repositories功能并指定扫描路径
 */
@Configuration
@ConditionalOnBean({
    EntityManagerFactory.class,
    PlatformTransactionManager.class
})
@EnableJpaRepositories(
    basePackages = "${spring.jpa.repository.packages}",
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
public class JpaRepositoryConfig {
    // 配置类为空，通过注解配置
}