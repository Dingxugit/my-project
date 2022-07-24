package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.dto.TShhYljfzbzDto;
import com.zhixiang.health.modules.t.model.entity.TShhYljfzbz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TShhYljfzbzParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 2021年油料经费指标账 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface TShhYljfzbzMapper extends BaseMapper<TShhYljfzbz> {

    List<TShhYljfzbzDto> pageList(TShhYljfzbzParam param);

    long pageTotal(TShhYljfzbzParam param);

    List<TShhYljfzbzDto> getDeptZbz(TShhYljfzbzParam param);

    List<TShhYljfzbzDto> pageEjdw(TShhYljfzbzParam param);

    TShhYljfzbz getDeptZbzBycarnum(@Param("year") Integer year, @Param("numberplate")String numberplate);

    TShhYljfzbz getCarZbzBycarnum(@Param("year")Integer year, @Param("numberplate")String numberplate);

    List<TShhYljfzbzDto> pageEjdwYdbb(TShhYljfzbzParam param);

    long getTotal(TShhYljfzbzParam param);

    Boolean searchExists(TShhYljfzbzParam param);

    List<TShhYljfzbz> getExportDataList(TShhYljfzbzParam param);
}
