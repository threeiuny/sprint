package cn.carey.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA Repositories配置类
 * 用于启用JPA Repositories功能并指定扫描路径
 */
@Configuration
@EnableJpaRepositories(
    basePackages = "${spring.jpa.repository.packages}",
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
public class JpaRepositoryConfig {
    // 配置类为空，通过注解配置
}