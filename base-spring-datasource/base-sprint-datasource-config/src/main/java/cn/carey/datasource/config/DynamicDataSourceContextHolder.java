package cn.carey.datasource.config;

public class DynamicDataSourceContextHolder {
    // 使用 ThreadLocal 存储当前线程的数据源类型
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }
}
