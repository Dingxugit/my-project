package com.zhixiang.health.modules.t.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.common.utils.poi.excel.ImportExcelUtil;
import com.zhixiang.health.modules.sys.model.dto.SysUserDto;
import com.zhixiang.health.modules.t.model.dto.TCarDto;
import com.zhixiang.health.modules.t.model.entity.TCar;
import com.zhixiang.health.modules.t.mapper.TCarMapper;
import com.zhixiang.health.modules.t.model.entity.TLqbzDcyhkp;
import com.zhixiang.health.modules.t.model.param.TCarParam;
import com.zhixiang.health.modules.t.service.ITCarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-01
 */
@Service
public class TCarServiceImpl extends ServiceImpl<TCarMapper, TCar> implements ITCarService {

    @Resource
    private TCarMapper mapper;

    @Override
    public Page<TCarDto> pageList(TCarParam param) {
        param.renderLimitStart();

        Page<TCarDto> pageResult = new Page<>();
        pageResult.setRecords(mapper.pageList(param))
                .setTotal(mapper.pageTotal(param));

        return pageResult;
    }

    @Override
    public List<TCarDto> getCarInfo(String firstDeptId) {
        return mapper.getCarInfo(firstDeptId);
    }

    @Override
    public List<TCarDto> getCarList(String firstDeptId) {
        List<TCarDto> list = mapper.getCarList(firstDeptId);
        mapper.updateCar(firstDeptId) ;
        return list ;
    }

    @Override
    public ServiceResult<String> importData(TCarParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TCar> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TCar.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(car -> {
                super.save(car);
            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public TCarDto getCarInfoBynum(TCarParam param) {
        return mapper.getCarInfoByNum(param.getNumberplate());
    }

    @Override
    public List<TCarDto> getCarInfoByDeptname(@Param("deptname") String deptname) {
        return mapper.getCarInfoByDeptname(deptname);
    }
}
