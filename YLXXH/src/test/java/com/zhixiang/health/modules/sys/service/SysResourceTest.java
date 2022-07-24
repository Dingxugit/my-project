package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.dto.SysRouteDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SysResourceTest {

    @Resource
    private ISysResourceService service;

    @Test
    void routeListTest() {
        SysRouteDto route = service.routeList();

        System.out.println(route);
    }
}
