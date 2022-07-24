package com.zhixiang.health.modules.sys.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.modules.sys.mapper.SysMenuMapper;
import com.zhixiang.health.modules.sys.model.dto.SysMenuDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class SysMenuTest {

    @Resource
    private SysMenuMapper mapper;

    @Test
    void listIndexByUserIdTest() {
        List<SysMenuDto> list = mapper.listIndexByUserId(1);

        Map<Integer, List<SysMenuDto>> map = list.stream().collect(Collectors.groupingBy(SysMenuDto::getResourceParentId));

        System.out.println(findIndex(25, map));

    }

    private String findIndex(Integer parentId, Map<Integer, List<SysMenuDto>> map) {
        List<SysMenuDto> list = map.get(parentId);
        if (CollUtil.isNotEmpty(list)) {

            for (SysMenuDto menu : list) {

                if (CollUtil.isEmpty(map.get(menu.getResourceId()))) {
                    return menu.getPath();
                } else {
                    return findIndex(menu.getResourceId(), map);
                }
            }
        }

        return StrUtil.EMPTY;
    }
}
