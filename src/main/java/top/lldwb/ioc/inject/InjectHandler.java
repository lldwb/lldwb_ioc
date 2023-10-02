package top.lldwb.ioc.inject;

import top.lldwb.ioc.factory.ContainerFactory;

/**
 * 注入处理器
 */
public interface InjectHandler {
    Object handle(Object object, ContainerFactory factory);
}
