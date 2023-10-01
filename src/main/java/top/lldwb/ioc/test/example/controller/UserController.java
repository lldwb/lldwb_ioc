package top.lldwb.ioc.test.example.controller;

import lombok.extern.slf4j.Slf4j;
import top.lldwb.ioc.Bean;
import top.lldwb.ioc.Inject;
import top.lldwb.ioc.test.example.service.UserService;

//@Component

//@Controller 控制层专属注解，取代 @Component
@Slf4j
@Bean("userController")
public class UserController {
//    @Autowired
    @Inject
    private UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

//    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void add(){
        log.debug("执行UserController");
        userService.add();
    }
}
