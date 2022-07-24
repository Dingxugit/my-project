package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TLqbzJygcjykrcgldjDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJygcjykrcgldj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzJygcjykrcgldjParam;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;

import java.util.List;

/**
 * <p>
 * 军油工程加油卡日常管理登记 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-17
 */
public interface TLqbzJygcjykrcgldjMapper extends BaseMapper<TLqbzJygcjykrcgldj> {

    List<TLqbzJygcjykrcgldjDto> pageList(TLqbzJygcjykrcgldjParam param);

    long getTotal(TLqbzJygcjykrcgldjParam param);

    List<TLqbzJygcjykrcgldj> getExportDataList(TLqbzJygcjykrcgldjParam param);
}
