package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.common.utils.TreeUtil;
import com.zhixiang.health.modules.sys.mapper.SysDeptMapper;
import com.zhixiang.health.modules.sys.model.dto.SysDeptTreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 系统机构部门管理 test
 * @Auther: HeJiawang
 * @Date: 2020-04-29
 */
@SpringBootTest
public class SysDeptTest {

    @Resource
    private ISysDeptService service;

    @Resource
    private SysDeptMapper mapper;

    @Test
    void treeTest() {
        List<SysDeptTreeDto> treeDtoList = mapper.listTreeDto();
        List<SysDeptTreeDto> treeList = TreeUtil.build(treeDtoList);

        System.out.println(treeList);
    }
}
