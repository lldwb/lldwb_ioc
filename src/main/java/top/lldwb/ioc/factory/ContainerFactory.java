package top.lldwb.ioc.factory;

import lombok.extern.slf4j.Slf4j;
import top.lldwb.ioc.Bean;
import top.lldwb.ioc.inject.InjectHandlerInvoker;
import top.lldwb.ioc.util.ScanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ContainerFactory {

    /**
     * 单例容器
     */
    private Map<String, Object> container = new HashMap<>();
    /**
     * 原型容器
     */
    private Map<String, Class<?>> archetype = new HashMap<>();

    /**
     * 初始化容器
     * 参数表示要管理的包路径
     */
    public ContainerFactory(String... packages) {
        List<Class<?>> classList = ScanUtils.scan(packages);
        resolveClass(classList);
    }

    /**
     * 容器工厂方法
     */
    public Object getBean(String name) {
        if (container.containsKey(name)) {
            return container.get(name);
        } else {
            Object object = newInstance(archetype.get(name));
            if (object == null) {
                throw new RuntimeException();
            }
            return inject(object);
        }
    }

    /**
     * 容器工厂方法
     */
    public <T> T getBean(String name, Class<T> tClass) {
        // 判断单例容器中是否有
        if (container.containsKey(name)) {
            return (T) container.get(name);
        } else {
            T t = (T) newInstance(archetype.get(name));
            if (t == null) {
                throw new RuntimeException();
            }
            inject(t);
            return t;
        }
    }

    /**
     * 容器工厂方法
     */
    public <T> T getBean(Class<T> tClass) {
        // 判断单例容器中是否有
        T t = null;
        for (Object objects : container.values()) {
            // 判断是否可以进行类型转换
            if (tClass.isInstance(objects)) {
                if (t != null) {
                    throw new RuntimeException();
                }
                t = (T) objects;
            }
        }
        for (Class<?> clazz : archetype.values()) {
            // 判断是否可以进行类型转换
            try {
                Object objects = clazz.newInstance();
                if (tClass.isInstance(objects)) {
                    if (t != null) {
                        throw new RuntimeException();
                    }
                    t = (T) objects;
                    inject(t);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        if (t == null) {
            throw new RuntimeException();
        }

        return t;
    }

    /**
     * 解析class集合，找到带有@Bean注解的类
     * 同时对单例容器进行依赖注入
     *
     * @param classList
     */
    private void resolveClass(List<Class<?>> classList) {
        classList.forEach(clazz -> {
            if (clazz.isAnnotationPresent(Bean.class)) {
                Bean bean = clazz.getAnnotation(Bean.class);
                // 获取 Bean 的作为 k
                String k = bean.value();
                // 判断是否需要唯一
                if (bean.sole()) {
                    // 唯一，添加到单例容器
                    container.put(k, newInstance(clazz));
                } else {
                    // 不唯一，添加到原型容器
                    archetype.put(k, clazz);
                }
            }
        });

        // 对单例容器进行依赖注入
        for (Object object : container.values()) {
            inject(object);
        }
    }

    /**
     * 判断接口是否多个实现类
     *
     * @param tClass
     * @return 有多个返回 false
     */
    public Boolean isMultipleClass(Class<?> tClass) {
        // 判断单例容器中是否有
        Boolean is = false;
        for (Object objects : container.values()) {
            // 判断是否可以进行类型转换
            if (tClass.isInstance(objects)) {
                if (is) {
                    return false;
                } else {
                    is = true;
                }
            }
        }
        for (Class<?> clazz : archetype.values()) {
            // 判断是否可以进行类型转换
            try {
                Object objects = clazz.newInstance();
                if (tClass.isInstance(objects)) {
                    if (is) {
                        return false;
                    } else {
                        is = true;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
        return is;
    }

    /**
     * 创建实例
     *
     * @param clazz 类
     * @return
     */
    private Object newInstance(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("实例创建失败:", e);
        }
    }

    /**
     * 进行依赖注入
     *
     * @param object
     * @return
     */
    private Object inject(Object object) {
        log.debug(object.getClass().getName() + "进行依赖注入");
        InjectHandlerInvoker.handle(object,this);
        return object;
    }
}