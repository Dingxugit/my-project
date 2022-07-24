package com.zhixiang.health.modules.t.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.common.utils.poi.excel.ImportExcelUtil;
import com.zhixiang.health.modules.sys.mapper.SysDeptMapper;
import com.zhixiang.health.modules.t.mapper.TCarMapper;
import com.zhixiang.health.modules.t.model.dto.TCarDto;
import com.zhixiang.health.modules.t.model.dto.TLqbzJykxxtjDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJykxxtj;
import com.zhixiang.health.modules.t.mapper.TLqbzJykxxtjMapper;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.service.ITLqbzJykxxtjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 加油卡信息统计 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-17
 */
@Service
public class TLqbzJykxxtjServiceImpl extends ServiceImpl<TLqbzJykxxtjMapper, TLqbzJykxxtj> implements ITLqbzJykxxtjService {

    @Resource
    private TLqbzJykxxtjMapper mapper;

    @Resource
    private TCarMapper carMapper ;

    @Resource
    private SysDeptMapper deptMapper ;

    @Override
    public Page<TLqbzJykxxtjDto> pageList(TLqbzJykxxtjParam param) {
        param.renderLimitStart() ;

        Page<TLqbzJykxxtjDto> result = new Page<>() ;
        result.setRecords(mapper.pageList(param)).setTotal(mapper.getTotal(param)) ;

        return result;
    }

    @Override
    public Boolean sjdwSave(TLqbzJykxxtj entity, UserParam param) {
        // 获取车辆信息
        TCarDto carDto = carMapper.getCarInfoByNum(entity.getNumberplate());
        if (carDto != null) {
            entity.setBrandmodel(carDto.getBrandmodel());
            entity.setEnginenumber(carDto.getEnginenumber());
            entity.setFramenumber(carDto.getFramenumber());
            entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName());
        }
        entity.setIsdc("N");
        entity.setIsdctp("N") ;
        return this.save(entity) ;
    }

    @Override
    public List<TLqbzJykxxtjDto> getJykxxtjList(TLqbzJykxxtjParam param) {
        List<TLqbzJykxxtjDto> list = mapper.getJykxxtjList(param);
        mapper.updateJykxx(param) ;
        return list ;
    }

    @Override
    public ServiceResult<String> importData(TLqbzJykxxtjParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TLqbzJykxxtj> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TLqbzJykxxtj.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(xxtj -> {
                super.save(xxtj);
            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public List<TLqbzJykxxtjDto> getJykxxtjLists(TLqbzJykxxtjParam param) {
        List<TLqbzJykxxtjDto> list = mapper.getJykxxtjList(param);
        return list ;
    }

    @Override
    public List<TLqbzJykxxtj> getJykxxtjDcLists(TLqbzJykxxtjParam param) {
        List<TLqbzJykxxtj> list= mapper.getJykxxtjDcLists(param);
        return list;
    }

    @Override
    public List<TLqbzJykxxtj> getExportDataList(TLqbzJykxxtjParam param) {
        return mapper.getExportDataList(param);
    }
}
