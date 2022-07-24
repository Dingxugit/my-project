package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyczpzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyczpz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyczpzParam;

import java.util.List;

/**
 * <p>
 * 油料供应充值凭证 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-18
 */
public interface TLqbzYlgyczpzMapper extends BaseMapper<TLqbzYlgyczpz> {

    List<TLqbzYlgyczpzDto> pageList(TLqbzYlgyczpzParam param);

    long getTotal(TLqbzYlgyczpzParam param);

    List<TLqbzYlgyczpz> getCzpzByCarnum(String numberplate);

    TLqbzYlgyczpzDto getLastCzpzBycarnum(TLqbzYlgyczpzParam param);

    TLqbzYlgyczpzDto getJypzById(TLqbzYlgyczpzParam param);

    List<TLqbzYlgyczpzDto> getCzpzList(TLqbzYlgyczpzParam param);

    void updateCzpz(TLqbzYlgyczpzParam param);

    List<TLqbzYlgyczpz> getExportDataList(TLqbzYlgyczpzParam param);
}
