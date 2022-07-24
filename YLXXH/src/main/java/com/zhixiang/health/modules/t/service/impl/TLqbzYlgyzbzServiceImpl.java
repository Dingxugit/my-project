package com.zhixiang.health.modules.t.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.common.utils.poi.excel.ImportExcelUtil;
import com.zhixiang.health.modules.sys.mapper.SysDeptMapper;
import com.zhixiang.health.modules.t.mapper.TLqbzYlgyczpzMapper;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyczpz;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.mapper.TLqbzYlgyzbzMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyzbzParam;
import com.zhixiang.health.modules.t.service.ITLqbzYlgyzbzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * 油料供应指标账 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-15
 */
@Service
public class TLqbzYlgyzbzServiceImpl extends ServiceImpl<TLqbzYlgyzbzMapper, TLqbzYlgyzbz> implements ITLqbzYlgyzbzService {

    @Resource
    private TLqbzYlgyzbzMapper mapper;

    @Resource
    private SysDeptMapper deptMapper ;

    @Resource
    private TLqbzYlgyczpzMapper ylgyczpzMapper ;

    @Override
    public Page<TLqbzYlgyzbzDto> pageList(TLqbzYlgyzbzParam param) {
        param.renderLimitStart();

        Page<TLqbzYlgyzbzDto> pageResult = new Page<>();
        pageResult.setRecords(mapper.pageList(param))
                .setTotal(mapper.pageTotal(param));

        return pageResult;
    }


    @Override
    public Boolean sjdwSave(TLqbzYlgyzbz entity, UserParam param) {
        // type=1 时，录入的为单位油料供应指标账
        if (entity.getType() == 1){
            // 获取登录人所在单位名称
            entity.setNumberplate(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;

            entity.setUnitinformation(entity.getNiandufenpei()
                    + entity.getZengbo()-entity.getZhuangong()
                    - (entity.getZhichuleixing35() == null ? 0: entity.getZhichuleixing35() )
                    - (entity.getZhichuleixing92() == null ? 0: entity.getZhichuleixing92() )
                    - (entity.getZhichuleixing95() == null ? 0: entity.getZhichuleixing92() )) ;
            entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        }else{// type=2 时，录入为车辆油料供应指标账
            // 根据车牌号查询油料供应充值凭证中是否存在该车已经打印充值但未入账信息（isrz=N）
            List<TLqbzYlgyczpz> czpzList = ylgyczpzMapper.getCzpzByCarnum(entity.getNumberplate()) ;

            // 根据年度及登录人所在单位，获取type=1的记录
            TLqbzYlgyzbz deptzbz = mapper.getDeptZbzBycarnum(entity.getYear(),deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName());
            entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
            AtomicReference<Double> zhichu92 = new AtomicReference<>(0d);
            AtomicReference<Double> zhichu95 = new AtomicReference<>(0d);
            AtomicReference<Double> zhichu35 = new AtomicReference<>(0d);
            czpzList.forEach(czpz ->{
                if (czpz != null) {

                    if ("92#".equals(czpz.getYoupin())) {
                        // 对应修改该车辆支出信息
//                        entity.setZhichuleixing92(czpz.getShuliang()) ;
                        zhichu92.updateAndGet(v -> new Double((double) (v + (entity.getZhichuleixing92() == null ? 0 : entity.getZhichuleixing92()) + czpz.getShuliang()))) ;
                        // 修改该车单位支出信息
                        deptzbz.setZhichuleixing92((deptzbz.getZhichuleixing92() == null? 0: deptzbz.getZhichuleixing92())+ (czpz.getShuliang())) ;
                    }

                    if ("95#".equals(czpz.getYoupin())) {
//                        entity.setZhichuleixing95(czpz.getShuliang()) ;
                        zhichu95.updateAndGet(v -> new Double((double) (v + (entity.getZhichuleixing95() == null ? 0 : entity.getZhichuleixing95()) + czpz.getShuliang()))) ;
                        deptzbz.setZhichuleixing95((deptzbz.getZhichuleixing95() == null? 0:deptzbz.getZhichuleixing95()) + (czpz.getShuliang())) ;
                    }
                    if ("-35#".equals(czpz.getYoupin())) {
//                        entity.setZhichuleixing35(czpz.getShuliang()) ;
                        zhichu35.updateAndGet(v -> new Double((double) (v + (entity.getZhichuleixing35() == null ? 0 : entity.getZhichuleixing35()) + czpz.getShuliang()))) ;
                        deptzbz.setZhichuleixing35((deptzbz.getZhichuleixing35() == null ? 0:deptzbz.getZhichuleixing35())  + (czpz.getShuliang())) ;
                    }
                    deptzbz.setUnitinformation(deptzbz.getNiandufenpei() + deptzbz.getZengbo() - deptzbz.getZhuangong()
                            -(deptzbz.getZhichuleixing92() == null? 0:deptzbz.getZhichuleixing92())
                            -(deptzbz.getZhichuleixing95() == null? 0:deptzbz.getZhichuleixing95())
                            -(deptzbz.getZhichuleixing35() == null? 0:deptzbz.getZhichuleixing35())) ;

                    this.updateById(deptzbz) ;
                    czpz.setIsrz("Y") ;// 改变状态为入账
                    ylgyczpzMapper.updateById(czpz) ;
                }
            });
            entity.setZhichuleixing92(zhichu92.get()) ;
            entity.setZhichuleixing95(zhichu95.get()) ;
            entity.setZhichuleixing35(zhichu35.get()) ;
            entity.setUnitinformation(entity.getNiandufenpei()
                    + entity.getZengbo()-entity.getZhuangong() -zhichu92.get() -zhichu95.get() -zhichu35.get() ) ;
        }

        return this.save(entity);
    }

    @Override
    public Boolean sjdwUpdate(TLqbzYlgyzbz entity, UserParam param) {
        if (entity.getType() == 1){
            entity.setNumberplate(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        }
        TLqbzYlgyzbz zbz = mapper.selectById(entity.getId()) ;

        entity.setUnitinformation(entity.getNiandufenpei() + entity.getZengbo()-entity.getZhuangong()
                - (zbz.getZhichuleixing35() == null ? 0: zbz.getZhichuleixing35() )
                - (zbz.getZhichuleixing92() == null ? 0: zbz.getZhichuleixing92() )
                - (zbz.getZhichuleixing95() == null ? 0: zbz.getZhichuleixing95() ))  ;
        return this.updateById(entity);
    }

    @Override
    public List<TLqbzYlgyzbzDto> getDeptZbz(TLqbzYlgyzbzParam param,UserParam param1) {
        param.setNumberplate(deptMapper.getSysDept(Integer.valueOf(param1.getFirstDeptId())).getName()) ;
        return mapper.getDeptZbz(param) ;
    }

    @Override
    public Boolean ejdwSave(TLqbzYlgyzbz entity, UserParam param) {
        entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setUnitinformation(
                (entity.getNiandufenpei() == null? 0 :entity.getNiandufenpei())
                        + (entity.getZengbo() == null? 0 :entity.getZengbo())
                        - (entity.getZhuangong() == null? 0 :entity.getZhuangong())
                        - (entity.getChongkazc() == null? 0 :entity.getChongkazc()));
        return this.save(entity);
    }

    @Override
    public Boolean ejdwUpdate(TLqbzYlgyzbz entity, UserParam param) {
        entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setUnitinformation(
                (entity.getNiandufenpei() == null? 0 :entity.getNiandufenpei())
                        + (entity.getZengbo() == null? 0 :entity.getZengbo())
                        - (entity.getZhuangong() == null? 0 :entity.getZhuangong())
                        - (entity.getChongkazc() == null? 0 :entity.getChongkazc()));
        return this.updateById(entity);
    }

    @Override
    public Page<TLqbzYlgyzbzDto> pageEjdw(TLqbzYlgyzbzParam param) {
        param.renderLimitStart();

        Page<TLqbzYlgyzbzDto> pageResult = new Page<>();
        pageResult.setRecords(mapper.pageEjdw(param))
                .setTotal(mapper.pageTotal(param));

        return pageResult;
    }

    /**
     * 导入数据
     * @param param excel文件路径、机构编码
     * @return 导入数据结果
     */
    @Override
    public ServiceResult<String> importData(TLqbzYlgyzbzParam param,UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TLqbzYlgyzbz> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TLqbzYlgyzbz.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        // 5、将excel文件中的数据存储至zbz
        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(zbz -> {
                zbz.setType(1) ;
                TLqbzYlgyzbz t = mapper.getDeptZbzBycarnum(zbz.getYear(),zbz.getNumberplate()) ;
                zbz.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(userParam.getFirstDeptId())).getName()) ;
                if (t == null){
                    super.save(zbz);
                }else{
                    t.setUnitinformation(zbz.getUnitinformation()) ;
                    t.setNiandufenpei(zbz.getNiandufenpei()) ;
                    t.setZengbo(zbz.getZengbo()) ;
                    t.setZhuangong(zbz.getZhuangong()) ;
                    t.setChongkazc(zbz.getChongkazc()) ;
                    mapper.updateById(t) ;
                }

            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public Page<TLqbzYlgyzbzDto> pageEjdwYdbb(TLqbzYlgyzbzParam param) {
        param.renderLimitStart() ;
        param.setStartDate(param.getStartDate()+ " 00:00:00" );
        param.setEndDate(param.getEndDate()+ " 23:59:59" );
        Page<TLqbzYlgyzbzDto> result = new Page<>() ;
        result.setRecords(mapper.pageEjdwYdbb(param)).setTotal(mapper.getTotal(param)) ;
        return result;
    }

    @Override
    public Boolean searchExists(TLqbzYlgyzbzParam param) {
        Boolean b = false ;
        TLqbzYlgyzbzDto dto = mapper.searchExists(param);
        if (dto != null) b = true ;
        return b;
    }

    @Override
    public List<TLqbzYlgyzbz> getExportDataList(TLqbzYlgyzbzParam param) {
        return mapper.getExportDataList(param);
    }
}
