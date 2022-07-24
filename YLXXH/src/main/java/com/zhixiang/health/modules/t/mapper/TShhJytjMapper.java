package com.zhixiang.health.modules.t.mapper;

import com.zhixiang.health.modules.t.model.dto.TShhJytjDto;
import com.zhixiang.health.modules.t.model.entity.TShhJytj;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.t.model.param.TShhJytjParam;

import java.util.List;

/**
 * <p>
 * 加油卡信息统计 Mapper 接口
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
public interface TShhJytjMapper extends BaseMapper<TShhJytj> {

    List<TShhJytjDto> pageList(TShhJytjParam param);

    long getTotal(TShhJytjParam param);

    List<TShhJytjDto> getJykxxtjList(TShhJytjParam param);

    void updateJytj(TShhJytjParam param);

    List<TShhJytj> getJykxxtjDcLists(TShhJytjParam param);

    List<TShhJytj> getExportDataList(TShhJytjParam param);
}
