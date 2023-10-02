package top.lldwb.ioc.inject.impl;

import lombok.extern.slf4j.Slf4j;
import top.lldwb.ioc.Inject;
import top.lldwb.ioc.factory.ContainerFactory;
import top.lldwb.ioc.inject.InjectHandler;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MethodInjectHandler implements InjectHandler {
    @Override
    public Object handle(Object object, ContainerFactory factory) {
        log.debug("遍历公开方法，查看是否需要依赖注入");
        try {
            // 获取 object 及其指定到父类 Object 的 BeanInfo
//            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass(), Object.class);
            // 方法信息
//            for (MethodDescriptor methodDescriptor : beanInfo.getMethodDescriptors()) {
//                Method method = methodDescriptor.getMethod();
            for (Method method : object.getClass().getMethods()) {
                if (method.isAnnotationPresent(Inject.class)) {
                    log.debug("method：" + method.getName());
                    List<Object> objectList = new ArrayList<>();
                    log.debug("获取方法的每个参数");
                    for (Parameter parameter : method.getParameters()) {
                        log.debug("遍历参数");
                        if (factory.isMultipleClass(parameter.getClass())) {
                            log.debug("一个实现类时执行");
                            log.debug("参数类型：" + parameter.getType().getName());
                            objectList.add(factory.getBean(parameter.getType()));
                        } else {
                            log.debug("多个实现类时执行");
                            Inject inject = method.getAnnotation(Inject.class);
                            log.debug("注解指定的实现类：" + inject.value());
                            objectList.add(factory.getBean(inject.value()));
                        }
                    }
                    method.invoke(object, objectList.stream().toArray());
                }
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
}
