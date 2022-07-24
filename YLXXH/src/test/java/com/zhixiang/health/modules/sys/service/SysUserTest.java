package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.common.model.enums.SexEnum;
import com.zhixiang.health.modules.sys.model.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description: 系统用户管理 test
 * @Auther: HeJiawang
 * @Date: 2020-04-17
 */
@SpringBootTest
public class SysUserTest {

    /**
     * service
     */
    @Resource
    private ISysUserService service;

    @Test
    void saveTest() {
        SysUser user = new SysUser()
                .setUserName("test1")
                .setPassword("1234561")
                .setRealName("测试1")
                .setSex(SexEnum.MAN)
                .setBirthday(new Date())
                .setPhone("13333333333");

        service.save(user);

        System.out.println(user.getId());
    }

    @Test
    void deleteTest() {
        service.removeById(2);
    }

    @Test
    void selectList() {
        List<SysUser> userList = service.list();
        System.out.println(userList);
    }

}
