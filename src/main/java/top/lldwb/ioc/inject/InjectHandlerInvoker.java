package top.lldwb.ioc.inject;

import lombok.extern.slf4j.Slf4j;
import top.lldwb.ioc.factory.ContainerFactory;
import top.lldwb.ioc.inject.impl.FieldInjectHandler;
import top.lldwb.ioc.inject.impl.MethodInjectHandler;
import top.lldwb.ioc.util.ScanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 注入调用者
 */
@Slf4j
public class InjectHandlerInvoker {
    private static List<InjectHandler> list=new ArrayList<>();

    static {
        for (Class<?> clazz:ScanUtils.scan(InjectHandler.class,"top.lldwb.ioc.inject.impl")){
            try {
                Object object = clazz.newInstance();
                if (InjectHandler.class.isInstance(object)){
                    list.add((InjectHandler) object);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Object handle(Object object, ContainerFactory factory) {
        for (InjectHandler injectHandler : list) {
            injectHandler.handle(object, factory);
        }
        return object;
    }
}
