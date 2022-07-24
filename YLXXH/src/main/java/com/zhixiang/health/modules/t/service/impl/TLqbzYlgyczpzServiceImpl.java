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
import com.zhixiang.health.modules.t.mapper.TLqbzYlgyzbzMapper;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyczpzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyczpz;
import com.zhixiang.health.modules.t.mapper.TLqbzYlgyczpzMapper;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyczpzParam;
import com.zhixiang.health.modules.t.service.ITLqbzYlgyczpzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 油料供应充值凭证 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-18
 */
@Service
public class TLqbzYlgyczpzServiceImpl extends ServiceImpl<TLqbzYlgyczpzMapper, TLqbzYlgyczpz> implements ITLqbzYlgyczpzService {

    @Resource
    private TLqbzYlgyczpzMapper mapper;

    @Resource
    private SysDeptMapper deptMapper ;

    @Resource
    private TLqbzYlgyzbzMapper ylgyzbzMapper ;


    @Override
    public Boolean sjdwSave(TLqbzYlgyczpz entity, UserParam param) {
        entity.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setIsrz("N");
        entity.setIsprint("N") ;
        entity.setIsdc("N");
        return this.save(entity);
    }

    @Override
    public Page<TLqbzYlgyczpzDto> pageList(TLqbzYlgyczpzParam param) {

        param.renderLimitStart() ;
        Page<TLqbzYlgyczpzDto> result = new Page<>() ;
        result.setRecords(mapper.pageList(param)).setTotal(mapper.getTotal(param)) ;

        return result;
    }


    @Override
    public TLqbzYlgyczpzDto getLastCzpzBycarnum(TLqbzYlgyczpzParam param) {
        return  mapper.getLastCzpzBycarnum(param) ;
    }

    @Override
    public TLqbzYlgyczpzDto getJypzById(TLqbzYlgyczpzParam param) {
        TLqbzYlgyczpzDto dto = mapper.getJypzById(param) ;
        TLqbzYlgyzbz zbz = ylgyzbzMapper.getCarZbzBycarnum(Integer.valueOf(dto.getChongzhisj().substring(0,4)),dto.getNumberplate()) ;
        if (zbz != null){
            if (dto.getShuliang() > zbz.getUnitinformation() ){
                dto.setIsbr(1) ;
            }else{
                dto.setIsbr(-1) ;
            }
        }
        return  dto ;
    }

    @Override
    public Boolean saveTozbz(TLqbzYlgyczpzParam param) {
        // 获取凭证
        TLqbzYlgyczpz czpz = mapper.selectById(param.getId()) ;

        if("N".equals(czpz.getIsrz())){
            // 获取单位指标账信息
            TLqbzYlgyzbz deptzbz = ylgyzbzMapper.getDeptZbzBycarnum(Integer.valueOf(czpz.getChongzhisj().substring(0,4)),czpz.getYongyoudanwei()) ;
            // 获取车辆指标账
            TLqbzYlgyzbz carzbz = ylgyzbzMapper.getCarZbzBycarnum(Integer.valueOf(czpz.getChongzhisj().substring(0,4)),czpz.getNumberplate()) ;

            if(carzbz != null){
                if ("92#".equals(czpz.getYoupin())){
                    carzbz.setZhichuleixing92((carzbz.getZhichuleixing92() == null ? 0 : carzbz.getZhichuleixing92()) + czpz.getShuliang()) ;
                }
                if ("95#".equals(czpz.getYoupin())){
                    carzbz.setZhichuleixing95((carzbz.getZhichuleixing95() == null ? 0 : carzbz.getZhichuleixing95()) + czpz.getShuliang()) ;
                }
                if ("-35#".equals(czpz.getYoupin())){
                    carzbz.setZhichuleixing35((carzbz.getZhichuleixing35() == null ? 0 : carzbz.getZhichuleixing35()) + czpz.getShuliang()) ;
                }
                carzbz.setUnitinformation((carzbz.getUnitinformation() == null ? 0 : carzbz.getUnitinformation()) - czpz.getShuliang()) ;
                ylgyzbzMapper.updateById(carzbz) ;
                czpz.setIsrz("Y") ;
            }
            if (deptzbz != null){
                if ("92#".equals(czpz.getYoupin())){
                    deptzbz.setZhichuleixing92((deptzbz.getZhichuleixing92() == null?0:deptzbz.getZhichuleixing92()) +czpz.getShuliang() ) ;
                }
                if ("95#".equals(czpz.getYoupin())){
                    deptzbz.setZhichuleixing95((deptzbz.getZhichuleixing95() == null?0:deptzbz.getZhichuleixing95()) +czpz.getShuliang() ) ;
                }
                if ("-35#".equals(czpz.getYoupin())){
                    deptzbz.setZhichuleixing35((deptzbz.getZhichuleixing35() == null?0:deptzbz.getZhichuleixing35()) +czpz.getShuliang() ) ;
                }
                deptzbz.setUnitinformation((deptzbz.getUnitinformation() == null ? 0 : deptzbz.getUnitinformation()) - czpz.getShuliang()) ;
                ylgyzbzMapper.updateById(deptzbz) ;
            }
        }

        return this.updateById(czpz);
    }

    @Override
    public List<TLqbzYlgyczpzDto> getCzpz(TLqbzYlgyczpzParam param) {
        List<TLqbzYlgyczpzDto> list = mapper.getCzpzList(param);
        mapper.updateCzpz(param);
        return list ;
    }

    @Override
    public ServiceResult<String> importData(TLqbzYlgyczpzParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TLqbzYlgyczpz> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TLqbzYlgyczpz.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(czpz -> {
                super.save(czpz);
            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public Boolean sjdwUpdate(TLqbzYlgyczpz entity, UserParam param) {
        // 获取凭证
        TLqbzYlgyczpz czpz = mapper.selectById(entity.getId()) ;
        // 如果两次数量不一致，则重新更新指标账
        if((czpz.getShuliang() != entity.getShuliang() && "Y".equals(czpz.getIsrz()))){
            double c = entity.getShuliang() -czpz.getShuliang() ;
            // 获取单位指标账信息
            TLqbzYlgyzbz deptzbz = ylgyzbzMapper.getDeptZbzBycarnum(Integer.valueOf(czpz.getChongzhisj().substring(0,4)),czpz.getYongyoudanwei()) ;
            // 获取车辆指标账
            TLqbzYlgyzbz carzbz = ylgyzbzMapper.getCarZbzBycarnum(Integer.valueOf(czpz.getChongzhisj().substring(0,4)),czpz.getNumberplate()) ;

            if(carzbz != null){
                if ("92#".equals(czpz.getYoupin())){
                    carzbz.setZhichuleixing92((carzbz.getZhichuleixing92() == null ? 0 : carzbz.getZhichuleixing92()) + c) ;
                }
                if ("95#".equals(czpz.getYoupin())){
                    carzbz.setZhichuleixing95((carzbz.getZhichuleixing95() == null ? 0 : carzbz.getZhichuleixing95()) + c) ;
                }
                if ("-35#".equals(czpz.getYoupin())){
                    carzbz.setZhichuleixing35((carzbz.getZhichuleixing35() == null ? 0 : carzbz.getZhichuleixing35()) + c) ;
                }
                carzbz.setUnitinformation((carzbz.getUnitinformation() == null ? 0 : carzbz.getUnitinformation()) - c) ;
                ylgyzbzMapper.updateById(carzbz) ;
                czpz.setIsrz("Y") ;
            }
            if (deptzbz != null){
                if ("92#".equals(czpz.getYoupin())){
                    deptzbz.setZhichuleixing92((deptzbz.getZhichuleixing92() == null?0:deptzbz.getZhichuleixing92()) +c ) ;
                }
                if ("95#".equals(czpz.getYoupin())){
                    deptzbz.setZhichuleixing95((deptzbz.getZhichuleixing95() == null?0:deptzbz.getZhichuleixing95()) +c) ;
                }
                if ("-35#".equals(czpz.getYoupin())){
                    deptzbz.setZhichuleixing35((deptzbz.getZhichuleixing35() == null?0:deptzbz.getZhichuleixing35()) +c) ;
                }
                deptzbz.setUnitinformation((deptzbz.getUnitinformation() == null ? 0 : deptzbz.getUnitinformation()) - c) ;
                ylgyzbzMapper.updateById(deptzbz) ;
            }
        }
        return this.updateById(entity);
    }

    @Override
    public List<TLqbzYlgyczpz> getExportDataList(TLqbzYlgyczpzParam param) {
        return mapper.getExportDataList(param);
    }
}
