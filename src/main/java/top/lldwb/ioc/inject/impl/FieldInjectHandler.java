package top.lldwb.ioc.inject.impl;

import lombok.extern.slf4j.Slf4j;
import top.lldwb.ioc.Inject;
import top.lldwb.ioc.factory.ContainerFactory;
import top.lldwb.ioc.inject.InjectHandler;

import java.lang.reflect.Field;

@Slf4j
public class FieldInjectHandler implements InjectHandler {
    @Override
    public Object handle(Object object, ContainerFactory factory) {log.debug("遍历所有字段，查看是否需要依赖注入(不推荐)");
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                log.debug("field：" + field.getName());
                Inject inject = field.getAnnotation(Inject.class);
                Object value;
                if (inject.value() != "") {
                    value = factory.getBean(inject.value());
                } else {
                    value = factory.getBean(field.getType());
                }
                try {
                    field.setAccessible(true);
                    field.set(object, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return object;
    }
}
