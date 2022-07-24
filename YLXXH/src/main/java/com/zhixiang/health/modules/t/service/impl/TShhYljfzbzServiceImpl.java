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
import com.zhixiang.health.modules.t.mapper.TShhGldjbMapper;
import com.zhixiang.health.modules.t.mapper.TShhJypzMapper;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.dto.TShhGldjbDto;
import com.zhixiang.health.modules.t.model.dto.TShhYljfzbzDto;
import com.zhixiang.health.modules.t.model.entity.*;
import com.zhixiang.health.modules.t.mapper.TShhYljfzbzMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyzbzParam;
import com.zhixiang.health.modules.t.model.param.TShhYljfzbzParam;
import com.zhixiang.health.modules.t.service.ITShhYljfzbzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 2021年油料经费指标账 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Service
public class TShhYljfzbzServiceImpl extends ServiceImpl<TShhYljfzbzMapper, TShhYljfzbz> implements ITShhYljfzbzService {

    @Resource
    private TShhYljfzbzMapper mapper;

    @Resource
    private SysDeptMapper deptMapper ;

    @Resource
    private TShhJypzMapper jypzMapper  ;

    @Resource
    private TShhGldjbMapper gldjbMapper ;

    @Override
    public Page<TShhYljfzbzDto> pageList(TShhYljfzbzParam param) {
        param.renderLimitStart();

        Page<TShhYljfzbzDto> pageResult = new Page<>();
        pageResult.setRecords(mapper.pageList(param))
                .setTotal(mapper.pageTotal(param));

        return pageResult;
    }


    @Override
    public Boolean sjdwSave(TShhYljfzbz entity, UserParam param) {
        // type=1 时，录入的为单位油料供应指标账
        if (entity.getType() == 1){
            // 获取登录人所在单位名称
            entity.setNumberplate(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;

            entity.setUnitinformation(entity.getNiandufenpei()
                    - entity.getZhukajingfei()
                    - (entity.getZhichuleixing35() == null ? 0: entity.getZhichuleixing35() )
                    - (entity.getZhichuleixing92() == null ? 0: entity.getZhichuleixing92() )
                    - (entity.getZhichuleixing95() == null ? 0: entity.getZhichuleixing92() )) ;

        }else{// type=2 时，录入为车辆油料供应指标账
            // 根据年度及登录人所在单位，获取type=1的记录
            TShhYljfzbz deptzbz = mapper.getDeptZbzBycarnum(entity.getYear(),deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName());

            // 录入车辆时，先检查该车有没有充值凭证已打印但未入账信息
            List<TShhJypz> czpzList = jypzMapper.getCzpzByCarnum(entity.getNumberplate()) ;
            AtomicReference<Double> zhichu92 = new AtomicReference<>(0d);
            AtomicReference<Double> zhichu95 = new AtomicReference<>(0d);
            AtomicReference<Double> zhichu35 = new AtomicReference<>(0d);
            czpzList.forEach(czpz ->{
                // 1.有充值凭证已打印但未入账信息,判断是否有日常管理登记信息，如果有，则进行入账
                TShhGldjbDto dto = gldjbMapper.getDjxx(czpz.getNumberplate(),czpz.getYoupin(),czpz.getJiayousj()) ;
                if (dto != null){
                    if ("92#".equals(dto.getYoupin())) {
                        // 对应修改该车辆支出信息
//                        entity.setZhichuleixing92(czpz.getShuliang()) ;
                        zhichu92.updateAndGet(v -> new Double((double) (v + (entity.getZhichuleixing92() == null ? 0 : entity.getZhichuleixing92()) + dto.getBencijiayoujine()))) ;
                        // 修改该车单位支出信息
                        deptzbz.setZhichuleixing92((deptzbz.getZhichuleixing92() == null? 0: deptzbz.getZhichuleixing92())+  dto.getBencijiayoujine()) ;
                    }

                    if ("95#".equals(dto.getYoupin())) {
//                        entity.setZhichuleixing95(czpz.getShuliang()) ;
                        zhichu95.updateAndGet(v -> new Double((double) (v + (entity.getZhichuleixing95() == null ? 0 : entity.getZhichuleixing95()) + dto.getBencijiayoujine()))) ;
                        deptzbz.setZhichuleixing95((deptzbz.getZhichuleixing95() == null? 0:deptzbz.getZhichuleixing95()) +  dto.getBencijiayoujine()) ;
                    }
                    if ("-35#".equals(dto.getYoupin())) {
//                        entity.setZhichuleixing35(czpz.getShuliang()) ;
                        zhichu35.updateAndGet(v -> new Double((double) (v + (entity.getZhichuleixing35() == null ? 0 : entity.getZhichuleixing35()) + dto.getBencijiayoujine()))) ;
                        deptzbz.setZhichuleixing35((deptzbz.getZhichuleixing35() == null ? 0:deptzbz.getZhichuleixing35())  +  dto.getBencijiayoujine()) ;
                    }
                    deptzbz.setUnitinformation(deptzbz.getNiandufenpei() - deptzbz.getZhukajingfei()
                            -(deptzbz.getZhichuleixing92() == null? 0: deptzbz.getZhichuleixing92())
                            -(deptzbz.getZhichuleixing95() == null? 0: deptzbz.getZhichuleixing95())
                            -(deptzbz.getZhichuleixing35() == null? 0: deptzbz.getZhichuleixing35())) ;

                    this.updateById(deptzbz) ;
                    czpz.setIsrz("Y") ;// 改变状态为入账
                    jypzMapper.updateById(czpz) ;
                }

                entity.setZhichuleixing92(zhichu92.get()) ;
                entity.setZhichuleixing95(zhichu95.get()) ;
                entity.setZhichuleixing35(zhichu35.get()) ;


            });
            // 2.没有充值凭证已打印但未入账信息,则忽略

            entity.setUnitinformation(entity.getNiandufenpei()
                    - entity.getZhukajingfei() -zhichu92.get() -zhichu95.get() -zhichu35.get() ) ;
        }
        entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;

        return this.save(entity);
    }

    @Override
    public Boolean sjdwUpdate(TShhYljfzbz entity, UserParam param) {

        entity.setUnitinformation(entity.getNiandufenpei() -entity.getZhukajingfei()
                - (entity.getZhichuleixing35() == null ? 0: entity.getZhichuleixing35() )
                - (entity.getZhichuleixing92() == null ? 0: entity.getZhichuleixing92() )
                - (entity.getZhichuleixing95() == null ? 0: entity.getZhichuleixing95() ))  ;
        return this.updateById(entity);
    }

    @Override
    public List<TShhYljfzbzDto> getDeptZbz(TShhYljfzbzParam param, UserParam param1) {
        param.setNumberplate(deptMapper.getSysDept(Integer.valueOf(param1.getFirstDeptId())).getName()) ;
        return mapper.getDeptZbz(param) ;
    }

    @Override
    public Page<TShhYljfzbzDto> pageEjdw(TShhYljfzbzParam param) {
        param.renderLimitStart();

        Page<TShhYljfzbzDto> pageResult = new Page<>();
        pageResult.setRecords(mapper.pageEjdw(param))
                .setTotal(mapper.pageTotal(param));

        return pageResult;
    }

    @Override
    public ServiceResult<String> importData(TShhYljfzbzParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TShhYljfzbz> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TShhYljfzbz.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(zbz -> {
                zbz.setType(1) ;
                TShhYljfzbz t = mapper.getDeptZbzBycarnum(zbz.getYear(),zbz.getNumberplate()) ;
                if (t == null){
                    super.save(zbz);
                }else{
                    t.setZhichuleixing35(zbz.getZhichuleixing35()) ;
                    t.setZhichuleixing95(zbz.getZhichuleixing95()) ;
                    t.setZhichuleixing92(zbz.getZhichuleixing92()) ;
                    t.setUnitinformation(zbz.getUnitinformation()) ;
                    t.setZhukajingfei(zbz.getZhukajingfei()) ;
                    t.setNiandufenpei(zbz.getNiandufenpei()) ;
                    mapper.updateById(t) ;
                }

            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public Boolean ejdwSave(TShhYljfzbz entity, UserParam param) {
        entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setUnitinformation(entity.getNiandufenpei() -entity.getZhukajingfei()
                - (entity.getZhichuleixing35() == null ? 0: entity.getZhichuleixing35() )
                - (entity.getZhichuleixing92() == null ? 0: entity.getZhichuleixing92() )
                - (entity.getZhichuleixing95() == null ? 0: entity.getZhichuleixing95() ))  ;
        return this.save(entity);
    }

    @Override
    public Boolean ejdwUpdate(TShhYljfzbz entity, UserParam param) {

        entity.setBianzhidanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setUnitinformation(entity.getNiandufenpei() -entity.getZhukajingfei()
                - (entity.getZhichuleixing35() == null ? 0: entity.getZhichuleixing35() )
                - (entity.getZhichuleixing92() == null ? 0: entity.getZhichuleixing92() )
                - (entity.getZhichuleixing95() == null ? 0: entity.getZhichuleixing95() ))  ;
        return this.updateById(entity);
    }

    @Override
    public Page<TShhYljfzbzDto> pageEjdwYdbb(TShhYljfzbzParam param) {
        param.renderLimitStart() ;
        param.setStartDate(param.getStartDate()+ " 00:00:00" );
        param.setEndDate(param.getEndDate()+ " 23:59:59" );
        Page<TShhYljfzbzDto> result = new Page<>() ;
        result.setRecords(mapper.pageEjdwYdbb(param)).setTotal(mapper.getTotal(param)) ;
        return result;
    }

    @Override
    public Boolean searchExists(TShhYljfzbzParam param) {
        return mapper.searchExists(param);
    }

    @Override
    public List<TShhYljfzbz> getExportDataList(TShhYljfzbzParam param) {
        return mapper.getExportDataList(param);
    }
}
