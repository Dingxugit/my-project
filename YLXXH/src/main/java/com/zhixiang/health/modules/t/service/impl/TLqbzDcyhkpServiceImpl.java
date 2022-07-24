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
import com.zhixiang.health.modules.t.model.dto.TLqbzDcyhkpDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzDcyhkp;
import com.zhixiang.health.modules.t.mapper.TLqbzDcyhkpMapper;
import com.zhixiang.health.modules.t.model.entity.TLqbzJykxxtj;
import com.zhixiang.health.modules.t.model.param.TLqbzDcyhkpParam;
import com.zhixiang.health.modules.t.service.ITLqbzDcyhkpService;
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
 * XXX单车油耗考核卡片 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-19
 */
@Service
public class TLqbzDcyhkpServiceImpl extends ServiceImpl<TLqbzDcyhkpMapper, TLqbzDcyhkp> implements ITLqbzDcyhkpService {

    @Resource
    private TLqbzDcyhkpMapper mapper;

    @Resource
    private SysDeptMapper deptMapper ;

    @Resource
    private TCarMapper carMapper ;

    @Override
    public Boolean sjdwSave(TLqbzDcyhkp entity, UserParam param) {
        entity.setYingyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setIsdc("N") ;
        return this.save(entity);
    }

    @Override
    public Boolean sjdwUpdate(TLqbzDcyhkp entity, UserParam param) {
        entity.setYingyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        return this.updateById(entity);
    }

    @Override
    public Page<TLqbzDcyhkpDto> pageList(TLqbzDcyhkpParam param) {
        param.renderLimitStart() ;
        Page<TLqbzDcyhkpDto> result = new Page<>();
        result.setTotal(mapper.getTotal(param)).setRecords(mapper.pageList(param) );
        return result;
    }

    @Override
    public TLqbzDcyhkpDto getLastGlsBycarnum(TLqbzDcyhkpParam param) {
        return  mapper.getLastGlsBycarnum(param) ;
    }

    @Override
    public List<TLqbzDcyhkpDto> getDcyhList(TLqbzDcyhkpParam param) {
        List<TLqbzDcyhkpDto> list = mapper.getDcyhList(param);
        mapper.updateDcyh(param) ;
        return list;
    }

    @Override
    public ServiceResult<String> importData(TLqbzDcyhkpParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TLqbzDcyhkp> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TLqbzDcyhkp.class);
        } catch (Exception e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return new ServiceResult<String>().error("导入数据失败，请检查数据格式");
        }

        if (CollUtil.isNotEmpty(dataList)) {
            dataList.forEach(dcyh -> {
                super.save(dcyh);
            });
        }

        return new ServiceResult<>("导入数据成功");
    }

    @Override
    public Map<String, Object> getDcyhxxBycarnum(TLqbzDcyhkpParam param) {
        String year = param.getChongzhisj();
        if (!"".equals(year))year = year.substring(0,4) ;
        param.setYear(Integer.valueOf(year)) ;
        TLqbzDcyhkp dcyhkp = mapper.getDcyhxxBycarnum(param) ;
        List<TLqbzDcyhkp> dcyhkplist = mapper.getDcyhxxList(param) ;
        TCarDto car = carMapper.getCarInfoByNum(param.getNumberplate());
        Map<String, Object> map = new HashMap() ;

        if (car != null ){
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
    public Page<TLqbzDcyhkpDto> pageEjdwYhfx(TLqbzDcyhkpParam param) throws ParseException {
        param.renderLimitStart() ;
        int monthSpace =  getMonthSpace(param.getStartDate(),param.getEndDate()) ;
        param.setMonthSpace(monthSpace) ;
        Page<TLqbzDcyhkpDto> result = new Page<TLqbzDcyhkpDto>() ;
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
    public TLqbzDcyhkp getDcyhxx(TLqbzDcyhkpParam param) {
        return mapper.getDcyhxx(param);
    }

    @Override
    public List<TLqbzDcyhkp> getDcyhxxList(TLqbzDcyhkpParam param) {
        return mapper.getDcyhxxList(param);
    }
}
