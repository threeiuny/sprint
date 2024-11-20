package cn.carey.jpa.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.jpa")
public class JpaProperties {
    /**
     * 数据库平台
     */
    private String databasePlatform;

    /**
     * 是否显示SQL
     */
    private boolean showSql = false;

    /**
     * 是否生成DDL
     */
    private boolean generateDdl = false;

    /**
     * 实体类扫描包路径
     */
    private String[] packagesToScan = {"cn.carey.jpa.entity"};

    /**
     * Hibernate特定配置
     */
    private final Map<String, String> properties = new HashMap<>();

    /**
     * Repository配置
     */
    private final Repository repository = new Repository();

    @Getter
    @Setter
    public static class Repository {
        /**
         * Repository扫描包路径
         */
        private String[] packages = {"cn.carey.jpa.repository"};
        
    }
    
}
