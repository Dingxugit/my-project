package com.zhixiang.health.modules.t.service.impl;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.modules.t.model.dto.TLqbzJygcjykrcgldjDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJygcjykrcgldj;
import com.zhixiang.health.modules.t.mapper.TLqbzJygcjykrcgldjMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzJygcjykrcgldjParam;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.service.ITLqbzJygcjykrcgldjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 军油工程加油卡日常管理登记 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-17
 */
@Service
public class TLqbzJygcjykrcgldjServiceImpl extends ServiceImpl<TLqbzJygcjykrcgldjMapper, TLqbzJygcjykrcgldj> implements ITLqbzJygcjykrcgldjService {

    @Resource
    private TLqbzJygcjykrcgldjMapper mapper;

    @Override
    public Page<TLqbzJygcjykrcgldjDto> pageList(TLqbzJygcjykrcgldjParam param) {
        param.renderLimitStart() ;
        Page<TLqbzJygcjykrcgldjDto> result = new Page() ;
        result.setRecords(mapper.pageList(param)).setTotal(mapper.getTotal(param));
        return result;
    }

    @Override
    public List<TLqbzJygcjykrcgldj> getExportDataList(TLqbzJygcjykrcgldjParam param) {
        return mapper.getExportDataList(param);
    }
}
