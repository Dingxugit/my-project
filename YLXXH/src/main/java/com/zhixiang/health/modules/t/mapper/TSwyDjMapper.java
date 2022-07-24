package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TSwyDjDto;
import com.zhixiang.health.modules.t.model.entity.TSwyDj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.model.param.TSwyDjParam;

import java.util.List;

/**
 * <p>
 * 价拨/实物油加注登记表 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
public interface TSwyDjMapper extends BaseMapper<TSwyDj> {

    List<TSwyDjDto> pageList(TSwyDjParam param);

    long getTotal(TSwyDjParam param);

    List<TSwyDjDto> getDjList(TSwyDjParam param);

    void updateDj(TSwyDjParam param);

    List<TSwyDj> getExportDataList(TSwyDjParam param);
}
