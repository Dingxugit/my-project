package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.common.utils.TreeUtil;
import com.zhixiang.health.modules.sys.mapper.SysDictMapper;
import com.zhixiang.health.modules.sys.model.dto.SysDictTreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 字典管理 test
 * @Auther: HeJiawang
 * @Date: 2020-04-29
 */
@SpringBootTest
public class SysDictTest {

    @Resource
    private SysDictMapper mapper;

    @Test
    void treeTest() {
        List<SysDictTreeDto> treeDtoList = mapper.listAllTreeDto();
        List<SysDictTreeDto> treeList = TreeUtil.build(treeDtoList);

        System.out.println(treeList);
    }
}
