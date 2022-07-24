package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TLqbzDcyhkpDto;
import com.zhixiang.health.modules.t.model.dto.TShhDckpDto;
import com.zhixiang.health.modules.t.model.entity.TShhDckp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TShhDckpParam;

import java.util.List;

/**
 * <p>
 * 油料社会化保障车辆单车耗油考核卡片 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface TShhDckpMapper extends BaseMapper<TShhDckp> {

    long getTotal(TShhDckpParam param);

    List<TShhDckpDto> pageList(TShhDckpParam param);

    TShhDckpDto getLastGlsBycarnum(TShhDckpParam param);

    List<TShhDckpDto> getDcyhList(TShhDckpParam param);

    TShhDckp getDcyhxxBycarnum(TShhDckpParam param);

    List<TShhDckp> getDcyhxxList(TShhDckpParam param);

    List<TShhDckpDto> pageEjdwYhfx(TShhDckpParam param);

    long getYhfxTotal(TShhDckpParam param);

    void updateDcyh(TShhDckpParam param);

    TShhDckp getDcyhxx(TShhDckpParam param);
}
