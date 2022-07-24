package com.zhixiang.health.modules.t.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.model.result.ServiceResult;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.common.utils.poi.excel.ImportExcelUtil;
import com.zhixiang.health.modules.sys.mapper.SysDeptMapper;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.dto.TSwySwylzDto;
import com.zhixiang.health.modules.t.model.entity.TShhYljfzbz;
import com.zhixiang.health.modules.t.model.entity.TSwySwylz;
import com.zhixiang.health.modules.t.mapper.TSwySwylzMapper;
import com.zhixiang.health.modules.t.model.param.TSwySwylzParam;
import com.zhixiang.health.modules.t.service.ITSwySwylzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 价拨/实物油料帐 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
@Service
public class TSwySwylzServiceImpl extends ServiceImpl<TSwySwylzMapper, TSwySwylz> implements ITSwySwylzService {

    @Resource
    private TSwySwylzMapper mapper;

    @Resource
    private SysDeptMapper deptMapper ;

    @Override
    public Page<TSwySwylzDto> pageList(TSwySwylzParam param) {
        param.renderLimitStart() ;

        Page<TSwySwylzDto> result = new Page<>() ;
        result.setRecords(mapper.pageList(param)).setTotal(mapper.getTotal(param)) ;
        return result;
    }

    @Override
    public List<TSwySwylzDto> getDeptZbzList(TSwySwylzParam param) {
        return mapper.getDeptZbzList(param);
    }

    @Override
    public Boolean sjdwSave(TSwySwylz entity, UserParam param) {
        entity.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setYouliaozhibiaojeicun(entity.getYouliaozhibiaoshouru() - (entity.getYouliaozhibiaozhichu()== null? 0 :entity.getYouliaozhibiaozhichu()));
        return this.save(entity);
    }

    @Override
    public Boolean sjdwUpdate(TSwySwylz entity, UserParam param) {
        TSwySwylz lz = mapper.selectById(entity.getId()) ;
        lz.setYouliaozhibiaojeicun(entity.getYouliaozhibiaoshouru() - (lz.getYouliaozhibiaozhichu()== null? 0 :lz.getYouliaozhibiaozhichu()));
        lz.setYouliaozhibiaoshouru(entity.getYouliaozhibiaoshouru()) ;
        return this.updateById(lz);
    }

    @Override
    public ServiceResult<String> importData(TSwySwylzParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TSwySwylz> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TSwySwylz.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(lz -> {
                TSwySwylz t = mapper.getSwylzxx(lz.getPingzhenghao(),lz.getYongyoudanwei()) ;
                if (t == null){
                    super.save(lz);
                }else{
                    t.setYouliaozhibiaoshouru(lz.getYouliaozhibiaoshouru()) ;
                    t.setRiqi(lz.getRiqi()) ;
                    t.setZhaiyao(lz.getZhaiyao()) ;
                    t.setYoupin(lz.getYoupin()) ;
                    t.setYouliaozhibiaozhichu(lz.getYouliaozhibiaozhichu()) ;
                    t.setYouliaozhibiaojeicun(lz.getYouliaozhibiaojeicun()) ;
                    mapper.updateById(t) ;
                }

            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public Boolean searchExists(TSwySwylzParam param) {
        Boolean b = false ;
        TSwySwylzDto dto = mapper.searchExists(param);
        if (dto != null) b = true ;
        return b;
    }

    @Override
    public List<TSwySwylz> getExportDataList(TSwySwylzParam param) {
        return mapper.getExportDataList(param);
    }
}
