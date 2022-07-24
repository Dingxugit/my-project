package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TShhJypzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyczpz;
import com.zhixiang.health.modules.t.model.entity.TShhJypz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.entity.TShhYljfzbz;
import com.zhixiang.health.modules.t.model.param.TShhJypzParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 油料社会化保障加油凭证 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface TShhJypzMapper extends BaseMapper<TShhJypz> {

    List<TShhJypzDto> pageList(TShhJypzParam param);

    long getTotal(TShhJypzParam param);

    TShhJypzDto getLastCzpzBycarnum(TShhJypzParam param);

    List<TShhJypzDto> getCzpzList(TShhJypzParam param);

    List<TShhJypz> getCzpzByCarnum(String numberplate);

    TShhJypzDto getJypzById(TShhJypzParam param);

    TShhJypz getJypzByCarnumAndSj(@Param("numberplate") String numberplate, @Param("jiayousj")String jiayousj, @Param("youpin")String youpin);

    void updateJypz(TShhJypzParam param);

    List<TShhJypz> getExportDataList(TShhJypzParam param);
}
