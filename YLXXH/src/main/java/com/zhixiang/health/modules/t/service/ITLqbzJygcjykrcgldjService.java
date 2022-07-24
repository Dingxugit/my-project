package com.zhixiang.health.modules.t.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.modules.t.model.dto.TLqbzJygcjykrcgldjDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJygcjykrcgldj;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.t.model.param.TLqbzJygcjykrcgldjParam;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;

import java.util.List;

/**
 * <p>
 * 军油工程加油卡日常管理登记 服务类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-17
 */
public interface ITLqbzJygcjykrcgldjService extends IService<TLqbzJygcjykrcgldj> {

    Page<TLqbzJygcjykrcgldjDto> pageList(TLqbzJygcjykrcgldjParam param);

    List<TLqbzJygcjykrcgldj> getExportDataList(TLqbzJygcjykrcgldjParam param);
}
