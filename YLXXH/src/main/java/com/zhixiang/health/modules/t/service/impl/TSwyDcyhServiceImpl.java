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
import com.zhixiang.health.modules.t.model.dto.TShhDckpDto;
import com.zhixiang.health.modules.t.model.dto.TSwyDcyhDto;
import com.zhixiang.health.modules.t.model.entity.TCar;
import com.zhixiang.health.modules.t.model.entity.TShhDckp;
import com.zhixiang.health.modules.t.model.entity.TSwyDcyh;
import com.zhixiang.health.modules.t.mapper.TSwyDcyhMapper;
import com.zhixiang.health.modules.t.model.param.TSwyDcyhParam;
import com.zhixiang.health.modules.t.service.ITSwyDcyhService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * XXX单车油耗卡片（实物油） 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
@Service
public class TSwyDcyhServiceImpl extends ServiceImpl<TSwyDcyhMapper, TSwyDcyh> implements ITSwyDcyhService {

    @Resource
    private TSwyDcyhMapper mapper;

    @Resource
    private SysDeptMapper deptMapper ;

    @Resource
    private TCarMapper carMapper ;

    @Override
    public Boolean sjdwSave(TSwyDcyh entity, UserParam param) {
        entity.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setIsdc("N") ;
        return this.save(entity);
    }

    @Override
    public Boolean sjdwUpdate(TSwyDcyh entity, UserParam param) {
        entity.setYongyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        return this.updateById(entity);
    }

    @Override
    public Page<TSwyDcyhDto> pageList(TSwyDcyhParam param) {
        param.renderLimitStart() ;
        Page<TSwyDcyhDto> result = new Page<>();
        result.setTotal(mapper.getTotal(param)).setRecords(mapper.pageList(param) );
        return result;
    }

    @Override
    public TSwyDcyhDto getLastGlsBycarnum(TSwyDcyhParam param) {
        return mapper.getLastGlsBycarnum(param);
    }

    @Override
    public List<TSwyDcyhDto> getDcyhList(TSwyDcyhParam param) {
        List<TSwyDcyhDto> list = mapper.getDcyhList(param) ;
        mapper.updateDcyh(param) ;
        return list;
    }

    @Override
    public ServiceResult<String> importData(TSwyDcyhParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TSwyDcyh> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TSwyDcyh.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(dckp -> {
                super.save(dckp);
            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public Map<String, Object> getDcyhxxBycarnum(TSwyDcyhParam param) {
        String year = param.getRiqi();
        if (!"".equals(year))year = year.substring(0,4) ;
        param.setYear(Integer.valueOf(year)) ;
        // 获取车辆信息
        TCarDto car = carMapper.getCarInfoByNum(param.getChepaihaoma());
        Map<String, Object> map = new HashMap() ;
        if (car != null){
//            param.setChepaihaoma(car.getNumberplate()) ;
            TSwyDcyh dcyhkp = mapper.getDcyhxxBycarnum(param) ;
            List<TSwyDcyh> dcyhkplist = mapper.getDcyhxxList(param) ;
            map.put("car",car) ;
            map.put("carInfo",dcyhkp) ;
            map.put("carInfoList",dcyhkplist) ;
        }else{
            map.put("car",null) ;
            map.put("carInfo",null) ;
            map.put("carInfoList",null) ;
        }


        return map;
    }

    @Override
    public Page<TSwyDcyhDto> pageEjdwYhfx(TSwyDcyhParam param) throws ParseException {
        param.renderLimitStart() ;
        int monthSpace =  getMonthSpace(param.getStartDate(),param.getEndDate()) ;
        param.setMonthSpace(monthSpace) ;
        Page<TSwyDcyhDto> result = new Page<TSwyDcyhDto>() ;
        result.setRecords(mapper.pageEjdwYhfx(param)).setTotal(mapper.getYhfxTotal(param)) ;
        return result;
    }

    /**
     * 计算两个日期间相差的月数
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2)
            throws ParseException {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        return Math.abs(result + 1);

    }

    @Override
    public List<TSwyDcyh> getDcyhxxList(TSwyDcyhParam param) {
        return mapper.getDcyhxxList(param);
    }

    @Override
    public TSwyDcyh getDcyhxx(TSwyDcyhParam param) {
        return mapper.getDcyhxx(param);
    }
}
