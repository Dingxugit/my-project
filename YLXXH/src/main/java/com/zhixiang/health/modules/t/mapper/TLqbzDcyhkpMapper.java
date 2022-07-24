package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TLqbzDcyhkpDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzDcyhkp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzDcyhkpParam;

import java.util.List;

/**
 * <p>
 * XXX单车油耗考核卡片 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-19
 */
public interface TLqbzDcyhkpMapper extends BaseMapper<TLqbzDcyhkp> {

    long getTotal(TLqbzDcyhkpParam param);

    List<TLqbzDcyhkpDto> pageList(TLqbzDcyhkpParam param);

    TLqbzDcyhkpDto getLastGlsBycarnum(TLqbzDcyhkpParam param);

    List<TLqbzDcyhkpDto> getDcyhList(TLqbzDcyhkpParam param);

    TLqbzDcyhkp getDcyhxxBycarnum(TLqbzDcyhkpParam param);

    List<TLqbzDcyhkp> getDcyhxxList(TLqbzDcyhkpParam param);

    List<TLqbzDcyhkpDto> pageEjdwYhfx(TLqbzDcyhkpParam param);

    long getYhfxTotal(TLqbzDcyhkpParam param);

    void updateDcyh(TLqbzDcyhkpParam param);

    TLqbzDcyhkp getDcyhxx(TLqbzDcyhkpParam param);
}
