package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyzbzParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 油料供应指标账 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-15
 */
public interface TLqbzYlgyzbzMapper extends BaseMapper<TLqbzYlgyzbz> {

    List<TLqbzYlgyzbzDto> pageList(TLqbzYlgyzbzParam param);

    long pageTotal(TLqbzYlgyzbzParam param);

    TLqbzYlgyzbz getDeptZbzBycarnum(@Param("year") Integer year , @Param("numberplate") String numberplate);

    TLqbzYlgyzbz getCarZbzBycarnum(@Param("year")Integer valueOf, @Param("numberplate")String numberplate);

    List<TLqbzYlgyzbzDto> getDeptZbz(TLqbzYlgyzbzParam param);

    List<TLqbzYlgyzbzDto> pageEjdw(TLqbzYlgyzbzParam param);

    List<TLqbzYlgyzbzDto> pageEjdwYdbb(TLqbzYlgyzbzParam param);

    long getTotal(TLqbzYlgyzbzParam param);

    TLqbzYlgyzbzDto searchExists(TLqbzYlgyzbzParam param);

    List<TLqbzYlgyzbz> getExportDataList(TLqbzYlgyzbzParam param);
}
