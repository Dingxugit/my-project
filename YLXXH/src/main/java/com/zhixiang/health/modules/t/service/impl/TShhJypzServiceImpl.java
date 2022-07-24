package com.zhixiang.health.modules.t.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.common.utils.poi.excel.ImportExcelUtil;
import com.zhixiang.health.modules.sys.mapper.SysDeptMapper;
import com.zhixiang.health.modules.t.mapper.TShhGldjbMapper;
import com.zhixiang.health.modules.t.mapper.TShhYljfzbzMapper;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyczpzDto;
import com.zhixiang.health.modules.t.model.dto.TShhGldjbDto;
import com.zhixiang.health.modules.t.model.dto.TShhJypzDto;
import com.zhixiang.health.modules.t.model.entity.*;
import com.zhixiang.health.modules.t.mapper.TShhJypzMapper;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyczpzParam;
import com.zhixiang.health.modules.t.model.param.TShhJypzParam;
import com.zhixiang.health.modules.t.service.ITShhJypzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 油料社会化保障加油凭证 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Service
public class TShhJypzServiceImpl extends ServiceImpl<TShhJypzMapper, TShhJypz> implements ITShhJypzService {

    @Resource
    private TShhJypzMapper mapper;

    @Resource
    private SysDeptMapper deptMapper ;

    @Resource
    private TShhGldjbMapper gldjbMapper ;

    @Resource
    private TShhYljfzbzMapper yljfzbzMapper ;

    @Override
    public Boolean sjdwSave(TShhJypz entity, UserParam param) {
        entity.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setIsrz("N");
        entity.setIsprint("N") ;
        entity.setIsdc("N") ;
        return this.save(entity);
    }

    @Override
    public Page<TShhJypzDto> pageList(TShhJypzParam param) {
        param.renderLimitStart() ;
        Page<TShhJypzDto> result = new Page<>() ;
        result.setRecords(mapper.pageList(param)).setTotal(mapper.getTotal(param)) ;

        return result;
    }

    @Override
    public TShhJypzDto getLastCzpzBycarnum(TShhJypzParam param) {
        return  mapper.getLastCzpzBycarnum(param) ;
    }

    @Override
    public List<TShhJypzDto> getCzpz(TShhJypzParam param) {
        List<TShhJypzDto>  list = mapper.getCzpzList(param) ;
        mapper.updateJypz(param) ;
        return list;
    }

    @Override
    public ServiceResult<String> importData(TShhJypzParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TShhJypz> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TShhJypz.class);
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
    public TShhJypzDto getJypzById(TShhJypzParam param) {
        TShhJypzDto dto = mapper.getJypzById(param) ;
        TShhGldjbDto djxx = gldjbMapper.getDjxx(dto.getNumberplate(),dto.getYoupin(),dto.getJiayousj()) ;
        TShhYljfzbz carzbz = yljfzbzMapper.getCarZbzBycarnum(Integer.valueOf(dto.getJiayousj().substring(0,4)),dto.getNumberplate()) ;
        if (djxx != null){ // 如果有登记信息
            if (carzbz != null){// 有指标账信息
                if (djxx.getBencijiayoujine() > carzbz.getUnitinformation()){
                    dto.setIsbr(1) ;// 结存不足
                }else{
                    dto.setIsbr(-1) ;
                }
            }else{
                dto.setIsbr(1) ;// 没有指标账，视为结存不足
            }
        }else{
            dto.setIsbr(0) ;// 没有录入登记信息
        }
        return  dto ;
    }

    @Override
    public Boolean saveTozbz(TShhJypzParam param) {
        // 获取凭证
        TShhJypz czpz = mapper.selectById(param.getId()) ;

        // 当前凭证未入账
        if("N".equals(czpz.getIsrz())){
            // 获取日常统计是否有该车加油信息
            TShhGldjbDto djxx = gldjbMapper.getDjxx(czpz.getNumberplate(),czpz.getYoupin(),czpz.getJiayousj()) ;
            if (djxx != null){
                // 获取单位指标账信息
                TShhYljfzbz deptzbz = yljfzbzMapper.getDeptZbzBycarnum(Integer.valueOf(czpz.getJiayousj().substring(0,4)),czpz.getYongyoudanwei()) ;
                // 获取车辆指标账
                TShhYljfzbz carzbz = yljfzbzMapper.getCarZbzBycarnum(Integer.valueOf(czpz.getJiayousj().substring(0,4)),czpz.getNumberplate()) ;

                if(carzbz != null){
                    if ("92#".equals(czpz.getYoupin())){
                        carzbz.setZhichuleixing92((carzbz.getZhichuleixing92() == null? 0:carzbz.getZhichuleixing92()) + djxx.getBencijiayoujine()) ;
                    }
                    if ("95#".equals(czpz.getYoupin())){
                        carzbz.setZhichuleixing95((carzbz.getZhichuleixing95() == null? 0:carzbz.getZhichuleixing95()) + djxx.getBencijiayoujine()) ;
                    }
                    if ("-35#".equals(czpz.getYoupin())){
                        carzbz.setZhichuleixing35((carzbz.getZhichuleixing35() == null? 0:carzbz.getZhichuleixing35()) + djxx.getBencijiayoujine()) ;
                    }
                    carzbz.setUnitinformation((carzbz.getUnitinformation() == null ? 0 : carzbz.getUnitinformation()) - djxx.getBencijiayoujine()) ;
                    yljfzbzMapper.updateById(carzbz) ;
                    czpz.setIsrz("Y") ;

                    if (deptzbz != null){
                        if ("92#".equals(czpz.getYoupin())){
                            deptzbz.setZhichuleixing92((deptzbz.getZhichuleixing92() == null? 0: deptzbz.getZhichuleixing92())+djxx.getBencijiayoujine() ) ;
                        }
                        if ("95#".equals(czpz.getYoupin())){
                            deptzbz.setZhichuleixing95((deptzbz.getZhichuleixing95() == null? 0: deptzbz.getZhichuleixing95()) +djxx.getBencijiayoujine() ) ;
                        }
                        if ("-35#".equals(czpz.getYoupin())){
                            deptzbz.setZhichuleixing35((deptzbz.getZhichuleixing35() == null? 0: deptzbz.getZhichuleixing35()) +djxx.getBencijiayoujine() ) ;
                        }
                        deptzbz.setUnitinformation((deptzbz.getUnitinformation() == null ? 0 : deptzbz.getUnitinformation()) - djxx.getBencijiayoujine()) ;
                        yljfzbzMapper.updateById(deptzbz) ;
                    }
                }

            }

        }
        czpz.setIsprint("Y") ;
        return this.updateById(czpz);
    }

    @Override
    public List<TShhJypz> getExportDataList(TShhJypzParam param) {
        List<TShhJypz> l = mapper.getExportDataList(param) ;
        return mapper.getExportDataList(param);
    }
}
