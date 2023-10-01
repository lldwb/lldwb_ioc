package top.lldwb.ioc.test.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.lldwb.ioc.Bean;
import top.lldwb.ioc.Inject;
import top.lldwb.ioc.test.example.dao.UserDao;
import top.lldwb.ioc.test.example.service.UserService;

//@Component("userService")
//@Service 业务层专属注解，取代 @Component
@Bean("userService")
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
        @Inject("userDao")
    private UserDao userDao;

    //    @Autowired
//    @Qualifier("userDaoFImpl")
//    @Inject("userDao")
//    @Inject
    public void setUserDao(UserDao userDaoFImpl) {
        this.userDao = userDaoFImpl;
    }

//    /**
//     * 可以通过构造方法来注入
//     *
//     * @param userDao
//     */
//    @Inject
//    @Named("userDao")
//    public UserServiceImpl(UserDao userDao) {
//        this.userDao = userDao;
//    }

    @Override
    public void add() {
        log.debug("执行UserServiceImpl");
        userDao.add();
    }
}
