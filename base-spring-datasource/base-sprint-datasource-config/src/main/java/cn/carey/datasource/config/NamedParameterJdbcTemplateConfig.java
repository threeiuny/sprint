package cn.carey.datasource.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ConditionalOnBean(name = "dynamicDataSource")
public class NamedParameterJdbcTemplateConfig {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(
            @Qualifier("dynamicDataSource") DataSource dataSource) {
        // 使用 dynamicDataSource 创建 NamedParameterJdbcTemplate
        return new NamedParameterJdbcTemplate(dataSource);
    }
}