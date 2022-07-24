package com.zhixiang.health.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Temp {

    @Test
    public void tempTest() {
       //  System.out.println(DateUtil.dayOfMonth(new Date()));

        System.out.println(DateUtil.thisYear());

        System.out.println(DateUtil.thisMonth());

        System.out.println(DateUtil.thisDayOfMonth());
    }
}
