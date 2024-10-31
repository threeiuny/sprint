# 主从数据源设计文档

## AbstractRoutingDataSource如何实现动态数据源

`AbstractRoutingDataSource`是Spring提供的一个抽象类，用于动态地从多个目标数据源中根据某种路由策略确定当前使用的数据源。其核心在于重写`determineCurrentLookupKey()`方法，该方法在每次获取数据库连接时都会被调用，以确定当前使用的数据源Key。

工作流程如下：

1. **定义数据源Key**：通过重写`determineCurrentLookupKey()`方法，返回一个标识当前数据源的Key。
2. **配置目标数据源**：在配置类中，将所有可能使用的数据源放入一个映射中，Key为数据源的标识，Value为实际的数据源实例。
3. **动态切换数据源**：在运行时，根据业务需求或上下文信息（如当前线程变量、请求参数等），动态决定返回哪个数据源的Key。

通过这种方式，应用程序可以在不改变业务代码的情况下，根据不同的策略动态地切换数据源，实现读写分离、分库分表等功能。

## 项目代码实现思路解析

本项目实现了读写分离的数据源配置，主要思路如下：

1. **定义数据源**

   在`DataSourceConfig`中，分别配置了一个主数据源（用于写操作）和两个从数据源（用于读操作）。使用`@ConfigurationProperties`注解从`application.yml`中读取数据源配置。

2. **创建动态数据源**

   `DynamicDataSource`继承自`AbstractRoutingDataSource`，重写了`determineCurrentLookupKey()`方法：

   - 使用`DynamicDataSourceContextHolder`获取当前线程上下文中的数据源Key。
   - 如果数据源Key为`slave`且有多个从数据源，则通过轮询的方式实现简单的负载均衡，返回一个从数据源的Key。
   - 否则，默认返回主数据源的Key，即`master`。

3. **管理数据源上下文**

   `DynamicDataSourceContextHolder`使用`ThreadLocal`保存当前线程的数据源类型（`master`或`slave`），提供设置、获取和清除数据源Key的方法。

4. **数据源AOP切面**

   `DataSourceAspect`通过AOP切面编程，在执行标注了`@SlaveDataSource`的方法之前，设置数据源Key为`slave`，方法执行完毕后清除数据源Key。这确保了读操作使用从数据源，而写操作默认使用主数据源。

5. **自定义注解**

   `@SlaveDataSource`是一个自定义注解，用于标识需要使用从数据源的方法。通过在方法上添加该注解，即可实现读写分离，且不侵入业务代码。

通过以上步骤，项目实现了基于`AbstractRoutingDataSource`的动态数据源切换，达到了读写分离的目的。
