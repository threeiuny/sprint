package cn.carey.datasource.aspect;

import cn.carey.datasource.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

/**
 * 数据源切面处理类
 */
@Aspect
@Component
@ConditionalOnBean(DataSource.class)
public class DataSourceAspect {

    @Pointcut("@annotation(cn.carey.datasource.annotation.SlaveDataSource)")
    public void slavePointcut() {
        // 切入点，匹配所有使用 @SlaveDataSource 注解的方法
    }

    @Before("slavePointcut()")
    public void beforeRead(JoinPoint joinPoint) {
        // 方法执行前，切换到读从据源
        DynamicDataSourceContextHolder.setDataSourceKey("slave");
    }

    @After("slavePointcut()")
    public void afterRead(JoinPoint joinPoint) {
        // 方法执行后，清除数据源设置
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }

    // 未标注 @SlaveDataSource 的方法，默认使用主数据源，无需处理
}
