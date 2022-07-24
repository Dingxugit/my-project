package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.dto.SysDictTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-30
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 校验字典值是否可用
     * @param dict 字典信息
     * @return <= 0 可用
     */
    int checkValue(SysDict dict);

    /**
     * 获取所有树结构字典信息
     * @return list
     */
    List<SysDictTreeDto> listAllTreeDto();

    /**
     * 根据字典值，获取字典信息
     * @param value 字典值
     * @return 字典信息
     */
    SysDict getByValue(@Param("value") String value);

}
