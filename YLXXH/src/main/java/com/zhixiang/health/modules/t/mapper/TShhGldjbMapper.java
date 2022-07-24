package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TShhGldjbDto;
import com.zhixiang.health.modules.t.model.entity.TShhGldjb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TShhGldjbParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 油料社会化保障加油卡日常管理登记表 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface TShhGldjbMapper extends BaseMapper<TShhGldjb> {

    List<TShhGldjbDto> pageList(TShhGldjbParam param);

    long getTotal(TShhGldjbParam param);

    TShhGldjbDto getDjxx(@Param("numberplate") String numberplate, @Param("youpin")String youpin, @Param("jiayoushijian")String jiayoushijian);

    List<TShhGldjb> getExportDataList(TShhGldjbParam param);
}
