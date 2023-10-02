package top.lldwb.ioc.inject;

import top.lldwb.ioc.factory.ContainerFactory;

public interface InjectHandler {
    Object handle(Object object, ContainerFactory factory);
}
