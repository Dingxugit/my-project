package com.zhixiang.health.modules.log.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.modules.log.mapper.LogHandleMapper;
import com.zhixiang.health.modules.log.model.dto.LogHandleDto;
import com.zhixiang.health.modules.log.model.entity.LogHandle;
import com.zhixiang.health.modules.log.model.param.LogSearchParam;
import com.zhixiang.health.modules.log.service.ILogHandleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 操作日志信息 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-07
 */
@Service
public class LogHandleServiceImpl implements ILogHandleService {

    @Resource
    private LogHandleMapper mapper;

    /**
     * 日志表前缀
     */
    private final String TABLE_NAME_START = "log_handle_";

    /**
     * 所有表名
     */
    private final List<String> TABLE_NAME_LIST = new ArrayList<String>(){
        {
            for ( int i = 1; i <= 12; i++){
                add(TABLE_NAME_START + i);
            }
        }
    };

    /**
     * 保存操作日志信息
     * @param logHandle 操作日志信息
     * @return boolean
     */
    @Override
    public Boolean save(LogHandle logHandle) {
        String tableName = TABLE_NAME_START + ( DateUtil.thisMonth() + 1 );
        logHandle.setYear(DateUtil.thisYear()).setDay(DateUtil.thisDayOfMonth());

        return mapper.insert(tableName, logHandle) >= 0;
    }

    /**
     * 获取分页信息
     * @param param param
     * @return 分页信息
     */
    @Override
    public Page<LogHandleDto> page(LogSearchParam param) {
        param.renderLimitStart();
        String tableName = TABLE_NAME_START + param.getMonth();

        List<LogHandleDto> resultList = CollUtil.newArrayList();
        List<LogHandle> list = mapper.pageList(tableName, param);
        if (CollUtil.isNotEmpty(list)) {
            list.forEach(log ->
                resultList.add(LogHandleDto.convertFromEntity(log))
            );
        }

        Page<LogHandleDto> pageResult = new Page<>();
        pageResult.setRecords(resultList)
                .setTotal(mapper.pageTotal(tableName, param));

        return pageResult;
    }
}
