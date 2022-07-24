package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TSwySwylzDto;
import com.zhixiang.health.modules.t.model.entity.TSwySwylz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TSwySwylzParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 价拨/实物油料帐 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
public interface TSwySwylzMapper extends BaseMapper<TSwySwylz> {

    List<TSwySwylzDto> pageList(TSwySwylzParam param);

    long getTotal(TSwySwylzParam param);

    List<TSwySwylzDto> getDeptZbzList(TSwySwylzParam param);

    TSwySwylz getSwylzxx(@Param("youpin") String youpin, @Param("yongyoudanwei")String yongyoudanwei);

    TSwySwylzDto searchExists(TSwySwylzParam param);

    List<TSwySwylz> getExportDataList(TSwySwylzParam param);
}
