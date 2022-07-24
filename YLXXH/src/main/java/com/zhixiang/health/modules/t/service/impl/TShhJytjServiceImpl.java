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
import com.zhixiang.health.modules.t.model.dto.TShhJytjDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJykxxtj;
import com.zhixiang.health.modules.t.model.entity.TShhJytj;
import com.zhixiang.health.modules.t.mapper.TShhJytjMapper;
import com.zhixiang.health.modules.t.model.param.TShhJytjParam;
import com.zhixiang.health.modules.t.service.ITShhJytjService;
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
 * @since 2021-06-20
 */
@Service
public class TShhJytjServiceImpl extends ServiceImpl<TShhJytjMapper, TShhJytj> implements ITShhJytjService {

    @Resource
    private TShhJytjMapper mapper;

    @Resource
    private TCarMapper carMapper ;

    @Resource
    private SysDeptMapper deptMapper ;

    @Override
    public Boolean sjdwSave(TShhJytj entity, UserParam param) {
        TCarDto carDto = carMapper.getCarInfoByNum(entity.getNumberplate());
        if (carDto != null) {
            entity.setChangpaixinghao(carDto.getBrandmodel());
            entity.setFadongjihao(carDto.getEnginenumber());
            entity.setChejiahaoma(carDto.getFramenumber());
            entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName());
        }
        entity.setIsdc("N") ;
        entity.setIsdctp("N") ;
        return this.save(entity) ;
    }

    @Override
    public Boolean sjdwUpdate(TShhJytj entity) {
        return null;
    }

    @Override
    public Page<TShhJytjDto> pageList(TShhJytjParam param) {
        param.renderLimitStart() ;

        Page<TShhJytjDto> result = new Page<>() ;
        result.setRecords(mapper.pageList(param)).setTotal(mapper.getTotal(param)) ;

        return result;
    }

    @Override
    public List<TShhJytjDto> getJykxxtjList(TShhJytjParam param) {
        List<TShhJytjDto> list =  mapper.getJykxxtjList(param);
        mapper.updateJytj(param);
        return list;
    }

    @Override
    public ServiceResult<String> importData(TShhJytjParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TShhJytj> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TShhJytj.class);
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
    public List<TShhJytjDto> getJykxxtjLists(TShhJytjParam param) {
        List<TShhJytjDto> list =  mapper.getJykxxtjList(param);
        return list;
    }

    @Override
    public List<TShhJytj> getJykxxtjDcLists(TShhJytjParam param) {
        List<TShhJytj> list =  mapper.getJykxxtjDcLists(param);
        return list;
    }

    @Override
    public List<TShhJytj> getExportDataList(TShhJytjParam param) {
        return mapper.getExportDataList(param);
    }
}
