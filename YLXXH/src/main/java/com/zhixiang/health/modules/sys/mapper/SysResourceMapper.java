package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.dto.SysResourceDto;
import com.zhixiang.health.modules.sys.model.dto.SysRouteTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统资源表 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {

    /**
     * 获取type值以上的资源信息
     * @param type type
     * @return list
     */
    List<SysResourceDto> list(@Param("type") SysResourceTypeEnum type);

    /**
     * 修改资源的上级资源id
     * @param resource 资源信息
     * @return true
     */
    int updateParentId(SysResource resource);

    /**
     * 删除指定表的数据
     * @param tableName 表名
     * @param id id
     * @return int
     */
    int deleteResource(@Param("tableName") String tableName, @Param("id") Integer id);

    /**
     * 获取前端路由信息
     * @return 路由信息
     */
    List<SysRouteTreeDto> routeList();
}
