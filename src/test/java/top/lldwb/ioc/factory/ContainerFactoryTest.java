package top.lldwb.ioc.factory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import top.lldwb.ioc.test.example.controller.UserController;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ContainerFactoryTest {

    @Test
    void getBean() {
        ContainerFactory factory = new ContainerFactory("top.lldwb.ioc.test.example");
//        log.debug(factory.getBean(UserController.class).getClass().getName());
        factory.getBean(UserController.class).add();
        factory.getBean("userController",UserController.class).add();
        ((UserController)factory.getBean("userController")).add();
    }

    @Test
    void testGetBean() {
    }

    @Test
    void testGetBean1() {
    }
}