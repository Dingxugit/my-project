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
import com.zhixiang.health.modules.t.model.dto.TShhDckpDto;
import com.zhixiang.health.modules.t.model.entity.TCar;
import com.zhixiang.health.modules.t.model.entity.TLqbzDcyhkp;
import com.zhixiang.health.modules.t.model.entity.TShhDckp;
import com.zhixiang.health.modules.t.mapper.TShhDckpMapper;
import com.zhixiang.health.modules.t.model.entity.TShhJypz;
import com.zhixiang.health.modules.t.model.param.TShhDckpParam;
import com.zhixiang.health.modules.t.service.ITShhDckpService;
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
 * 油料社会化保障车辆单车耗油考核卡片 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Service
public class TShhDckpServiceImpl extends ServiceImpl<TShhDckpMapper, TShhDckp> implements ITShhDckpService {

    @Resource
    private TShhDckpMapper mapper;

    @Resource
    private SysDeptMapper deptMapper ;

    @Resource
    private TCarMapper carMapper ;

    @Override
    public Boolean sjdwSave(TShhDckp entity, UserParam param) {
        entity.setYingyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setIsdc("N") ;
        return this.save(entity);
    }

    @Override
    public Boolean sjdwUpdate(TShhDckp entity, UserParam param) {
        entity.setYingyoudanwei(deptMapper.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        return this.updateById(entity);
    }

    @Override
    public Page<TShhDckpDto> pageList(TShhDckpParam param) {
        param.renderLimitStart() ;
        Page<TShhDckpDto> result = new Page<>();
        result.setTotal(mapper.getTotal(param)).setRecords(mapper.pageList(param) );
        return result;
    }

    @Override
    public TShhDckpDto getLastGlsBycarnum(TShhDckpParam param) {
        return mapper.getLastGlsBycarnum(param);
    }

    @Override
    public List<TShhDckpDto> getDcyhList(TShhDckpParam param) {
        List<TShhDckpDto> list = mapper.getDcyhList(param);
        mapper.updateDcyh(param) ;
        return list;
    }

    @Override
    public ServiceResult<String> importData(TShhDckpParam param, UserParam userParam) {
        String deptId = userParam.getFirstDeptId();
        if (StrUtil.isEmpty(deptId)) {
            return new ServiceResult<String>().error("系统异常，请联系管理员");
        }

        // 1、将excel文件中的数据构建成数据实体，如果报错，返回错误信息
        List<TShhDckp> dataList;
        try {
            ImportExcelUtil importExcelUtil = new ImportExcelUtil(
                    new File(FileUtil.FileBuilder.staticFilePath + param.getWenjianlj()), 0);
            dataList = importExcelUtil.getDataList(TShhDckp.class);
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
    public Map<String, Object> getDcyhxxBycarnum(TShhDckpParam param) {
        String year = param.getChongzhisj();
        if (!"".equals(year))year = year.substring(0,4) ;
        param.setYear(Integer.valueOf(year)) ;
        TShhDckp dcyhkp = mapper.getDcyhxxBycarnum(param) ;
        List<TShhDckp> dcyhkplist = mapper.getDcyhxxList(param) ;
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
    public Page<TShhDckpDto> pageEjdwYhfx(TShhDckpParam param) throws ParseException {
        param.renderLimitStart() ;
        int monthSpace =  getMonthSpace(param.getStartDate(),param.getEndDate()) ;
        param.setMonthSpace(monthSpace) ;
        Page<TShhDckpDto> result = new Page<TShhDckpDto>() ;
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
    public List<TShhDckp> getDcyhxxList(TShhDckpParam param) {
        return mapper.getDcyhxxList(param);
    }

    @Override
    public TShhDckp getDcyhxx(TShhDckpParam param) {
        return  mapper.getDcyhxx(param);
    }
}
