package com.zhixiang.health.modules.t.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.common.utils.poi.excel.ImportExcelUtil;
import com.zhixiang.health.modules.sys.mapper.SysDeptMapper;
import com.zhixiang.health.modules.t.mapper.TSwySwylzMapper;
import com.zhixiang.health.modules.t.model.dto.TSwyDjDto;
import com.zhixiang.health.modules.t.model.entity.TSwyDj;
import com.zhixiang.health.modules.t.mapper.TSwyDjMapper;
import com.zhixiang.health.modules.t.model.entity.TSwySwylz;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.model.param.TSwyDjParam;
import com.zhixiang.health.modules.t.service.ITSwyDjService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 价拨/实物油加注登记表 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
@Service
public class TSwyDjServiceImpl extends ServiceImpl<TSwyDjMapper, TSwyDj> implements ITSwyDjService {

    @Resource
    private TSwyDjMapper mapper;

    @Resource
    private TSwySwylzMapper swySwylzMapper ;

    @Resource
    private SysDeptMapper deptMapper ;

    @Override
    public Page<TSwyDjDto> pageList(TSwyDjParam param) {
        param.renderLimitStart() ;
        Page<TSwyDjDto> result = new Page<>() ;
        result.setRecords(mapper.pageList(param)).setTotal(mapper.getTotal(param)) ;
        return result;
    }

    @Override
    public Boolean sjdwSave(TSwyDj entity, UserParam param) {
        // 先根据油品、用油单位查询是否存在价拨/实物油料账记录
        TSwySwylz swylz = swySwylzMapper.getSwylzxx(entity.getYoupin(),deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        if (swylz != null){// 存在，直接修改支出
            swylz.setYouliaozhibiaozhichu(
                    (double) Math.round(((swylz.getYouliaozhibiaozhichu() == null?0:swylz.getYouliaozhibiaozhichu()) + entity.getYouliaozhibiaojeicun() * entity.getMidu())*100) /100
            ) ;
            swylz.setYouliaozhibiaojeicun((double)Math.round((swylz.getYouliaozhibiaojeicun()-entity.getYouliaozhibiaojeicun() * entity.getMidu())*100)/100) ;
        }
//        else{
//            swylz = new TSwySwylz() ; //不存在，先同步一条该凭证号的价拨/实物油料账记录
//            swylz.setYouliaozhibiaozhichu((double)Math.round(entity.getYouliaozhibiaojeicun() * entity.getMidu() *100) /100) ;
////            swylz.setPingzhenghao(entity.getPingzhenghao()) ;
//            swylz.setYouliaozhibiaojeicun((double)Math.round((entity.getYouliaozhibiaojeicun() * entity.getMidu())*100)/100) ;
//        }
//        swylz.setRiqi(entity.getRiqi()) ;
//        swylz.setZhaiyao(entity.getZhuangbeihao()) ;
//        swylz.setYoupin(entity.getYoupin()) ;
//        swylz.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
//        if (swylz.getId() != null){
            swySwylzMapper.updateById(swylz);
//        }else {
//            swySwylzMapper.insert(swylz) ;
//        }
        entity.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setIsdc("N") ;
        return this.save(entity);
    }

    @Override
    public Boolean sjdwUpdate(TSwyDj entity, UserParam param) {
        TSwyDj dj = mapper.selectById(entity.getId()) ;
        // 当加油量或者密度发生变化时，更新价拨/实物油料账对应记录
        if(dj.getMidu()*dj.getYouliaozhibiaojeicun() != entity.getMidu()*entity.getYouliaozhibiaojeicun()){
            // 先根据凭证号、用油单位查询是否存在价拨/实物油料账记录
            TSwySwylz swylz = swySwylzMapper.getSwylzxx(entity.getYoupin(),deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
            if (swylz != null){
                swylz.setYouliaozhibiaozhichu(// 支出为原来的支出+变化的值
                        (swylz.getYouliaozhibiaozhichu() == null?0:swylz.getYouliaozhibiaozhichu())
                        + ((double)Math.round((entity.getMidu()*entity.getYouliaozhibiaojeicun() - (dj.getMidu()*dj.getYouliaozhibiaojeicun()))*100) /100)) ;
            }
//            else{
//                swylz = new TSwySwylz() ;
//                swylz.setYouliaozhibiaozhichu((double)Math.round(entity.getYouliaozhibiaojeicun() * entity.getMidu() *100) /100) ;
//            }
//            swylz.setRiqi(entity.getRiqi()) ;
//            swylz.setZhaiyao(entity.getZhuangbeihao()) ;
//            swylz.setYoupin(entity.getYoupin()) ;
//            swylz.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
//            if (swylz.getId() != null){
                swySwylzMapper.updateById(swylz);
//            }else {
//                swySwylzMapper.insert(swylz) ;
//            }
        }
        entity.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        return this.updateById(entity);
    }

    @Override
    public ServiceResult<String> importData(TSwyDjParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TSwyDj> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TSwyDj.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(dj -> {
                super.save(dj);
            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public List<TSwyDjDto> getDjList(TSwyDjParam param) {
        List<TSwyDjDto> list = mapper.getDjList(param);
        mapper.updateDj(param) ;
        return list;
    }

    @Override
    public List<TSwyDj> getExportDataList(TSwyDjParam param) {
        return mapper.getExportDataList(param);
    }
}
