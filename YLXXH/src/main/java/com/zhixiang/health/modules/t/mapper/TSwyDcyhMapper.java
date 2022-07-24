package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TSwyDcyhDto;
import com.zhixiang.health.modules.t.model.entity.TSwyDcyh;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TSwyDcyhParam;

import java.util.List;

/**
 * <p>
 * XXX单车油耗卡片（实物油） Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
public interface TSwyDcyhMapper extends BaseMapper<TSwyDcyh> {

    long getTotal(TSwyDcyhParam param);

    List<TSwyDcyhDto> pageList(TSwyDcyhParam param);

    TSwyDcyhDto getLastGlsBycarnum(TSwyDcyhParam param);

    TSwyDcyh getDcyhxxBycarnum(TSwyDcyhParam param);

    List<TSwyDcyh> getDcyhxxList(TSwyDcyhParam param);

    List<TSwyDcyhDto> getDcyhList(TSwyDcyhParam param);

    List<TSwyDcyhDto> pageEjdwYhfx(TSwyDcyhParam param);

    long getYhfxTotal(TSwyDcyhParam param);

    void updateDcyh(TSwyDcyhParam param);

    TSwyDcyh getDcyhxx(TSwyDcyhParam param);

}
