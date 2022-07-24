package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TLqbzJykxxtjDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJykxxtj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;

import java.util.List;

/**
 * <p>
 * 加油卡信息统计 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-17
 */
public interface TLqbzJykxxtjMapper extends BaseMapper<TLqbzJykxxtj> {

    List<TLqbzJykxxtjDto> pageList(TLqbzJykxxtjParam param);

    long getTotal(TLqbzJykxxtjParam param);

    List<TLqbzJykxxtjDto> getJykxxtjList(TLqbzJykxxtjParam param);

    void updateJykxx(TLqbzJykxxtjParam param);

    List<TLqbzJykxxtj> getJykxxtjDcLists(TLqbzJykxxtjParam param);

    List<TLqbzJykxxtj> getExportDataList(TLqbzJykxxtjParam param);
}
