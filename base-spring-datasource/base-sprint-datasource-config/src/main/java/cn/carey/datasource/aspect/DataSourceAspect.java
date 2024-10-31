package cn.carey.datasource.aspect;

import cn.carey.datasource.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(cn.carey.datasource.annotation.ReadOnly)")
    public void readPointcut() {
        // 切入点，匹配所有使用 @ReadOnly 注解的方法
    }

    @Before("readPointcut()")
    public void beforeRead(JoinPoint joinPoint) {
        // 方法执行前，切换到读数据源
        DynamicDataSourceContextHolder.setDataSourceKey("read");
    }

    @After("readPointcut()")
    public void afterRead(JoinPoint joinPoint) {
        // 方法执行后，清除数据源设置
        DynamicDataSourceContextHolder.clearDataSourceKey();
    }

    // 未标注 @ReadOnly 的方法，默认使用写数据源，无需处理
}
