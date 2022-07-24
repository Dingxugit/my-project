package com.zhixiang.health.modules.t.service.impl;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.t.mapper.TShhJypzMapper;
import com.zhixiang.health.modules.t.mapper.TShhYljfzbzMapper;
import com.zhixiang.health.modules.t.model.dto.TShhGldjbDto;
import com.zhixiang.health.modules.t.model.dto.TShhYljfzbzDto;
import com.zhixiang.health.modules.t.model.entity.TShhGldjb;
import com.zhixiang.health.modules.t.mapper.TShhGldjbMapper;
import com.zhixiang.health.modules.t.model.entity.TShhJypz;
import com.zhixiang.health.modules.t.model.entity.TShhYljfzbz;
import com.zhixiang.health.modules.t.model.param.TShhGldjbParam;
import com.zhixiang.health.modules.t.service.ITShhGldjbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 油料社会化保障加油卡日常管理登记表 服务实现类
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@Service
public class TShhGldjbServiceImpl extends ServiceImpl<TShhGldjbMapper, TShhGldjb> implements ITShhGldjbService {

    @Resource
    private TShhGldjbMapper mapper;

    @Resource
    private TShhJypzMapper jypzMapper ;

    @Resource
    private TShhYljfzbzMapper shhYljfzbzMapper ;

    @Override
    public Page<TShhGldjbDto> pageList(TShhGldjbParam param) {
        param.renderLimitStart() ;

        Page<TShhGldjbDto> result = new Page<>() ;
        result.setRecords(mapper.pageList(param)).setTotal(mapper.getTotal(param)) ;
        return result;
    }

    @Override
    public Boolean sjdwSave(TShhGldjb entity, UserParam param) {
        // 先获取充值凭证是否有该车未入账信息
        TShhJypz jypz = jypzMapper.getJypzByCarnumAndSj(entity.getNumberplate(),entity.getJiayoushijian(),entity.getYoupin());
        // 如果有，获取指标账，如果录入了指标账，则进行更改
        if (jypz != null){
            TShhYljfzbz dept= shhYljfzbzMapper.getDeptZbzBycarnum(Integer.valueOf(jypz.getJiayousj().substring(0,4)),jypz.getYongyoudanwei());
            TShhYljfzbz car = shhYljfzbzMapper.getCarZbzBycarnum(Integer.valueOf(jypz.getJiayousj().substring(0,4)),jypz.getNumberplate()) ;

            if (car != null){
                if ("92#".equals(jypz.getYoupin())){
                    car.setZhichuleixing92((car.getZhichuleixing92() == null? 0:car.getZhichuleixing92()) + entity.getBencijiayoujine()) ;
                }
                if ("95#".equals(jypz.getYoupin())){
                    car.setZhichuleixing95((car.getZhichuleixing95() == null? 0:car.getZhichuleixing95()) + entity.getBencijiayoujine()) ;
                }
                if ("-35#".equals(jypz.getYoupin())){
                    car.setZhichuleixing35((car.getZhichuleixing35() == null? 0:car.getZhichuleixing35()) + entity.getBencijiayoujine()) ;
                }
                car.setUnitinformation((car.getUnitinformation() == null? 0:car.getUnitinformation()) - entity.getBencijiayoujine()) ;
                shhYljfzbzMapper.updateById(car) ;
                jypz.setIsrz("Y") ;
                jypzMapper.updateById(jypz) ;
            }

            if (dept != null){
                if ("92#".equals(jypz.getYoupin())){
                    dept.setZhichuleixing92((dept.getZhichuleixing92() ==null? 0:dept.getZhichuleixing92()) +entity.getBencijiayoujine() ) ;
                }
                if ("95#".equals(jypz.getYoupin())){
                    dept.setZhichuleixing95((dept.getZhichuleixing95() ==null? 0:dept.getZhichuleixing95()) +entity.getBencijiayoujine() ) ;
                }
                if ("-35#".equals(jypz.getYoupin())){
                    dept.setZhichuleixing35((dept.getZhichuleixing35() ==null? 0:dept.getZhichuleixing35()) +entity.getBencijiayoujine() ) ;
                }
                dept.setUnitinformation((dept.getUnitinformation()== null? 0:dept.getUnitinformation()) - entity.getBencijiayoujine()) ;
                shhYljfzbzMapper.updateById(dept) ;
            }

        }
        return this.save(entity) ;
    }

    @Override
    public Boolean sjdwUpdate(TShhGldjb entity, UserParam param) {
        TShhGldjb djb = mapper.selectById(entity.getId()) ;
        if(djb.getBencijiayoujine() != entity.getBencijiayoujine()){
            double bencijine = entity.getBencijiayoujine() -djb.getBencijiayoujine() ;
            // 先获取充值凭证是否有已打印信息
            TShhJypz jypz = jypzMapper.getJypzByCarnumAndSj(entity.getNumberplate(),entity.getJiayoushijian(),entity.getYoupin());
            // 如果有，获取指标账，如果录入了指标账，则进行更改
            if (jypz != null){
                TShhYljfzbz dept= shhYljfzbzMapper.getDeptZbzBycarnum(Integer.valueOf(jypz.getJiayousj().substring(0,4)),jypz.getYongyoudanwei());
                TShhYljfzbz car = shhYljfzbzMapper.getCarZbzBycarnum(Integer.valueOf(jypz.getJiayousj().substring(0,4)),jypz.getNumberplate()) ;

                if (car != null){// 经费指标账中该车存在信息
                    if ("92#".equals(jypz.getYoupin())){
                        car.setZhichuleixing92((car.getZhichuleixing92() == null? 0 : car.getZhichuleixing92()) + bencijine) ;
                    }
                    if ("95#".equals(jypz.getYoupin())){
                        car.setZhichuleixing95((car.getZhichuleixing95() == null? 0 : car.getZhichuleixing95()) + bencijine) ;
                    }
                    if ("-35#".equals(jypz.getYoupin())){
                        car.setZhichuleixing35((car.getZhichuleixing35() == null? 0 : car.getZhichuleixing35()) + bencijine) ;
                    }
                    car.setUnitinformation((car.getUnitinformation() == null ? 0:car.getUnitinformation()) - bencijine) ;
                    shhYljfzbzMapper.updateById(car) ;
                    jypz.setIsrz("Y") ;
                    jypzMapper.updateById(jypz) ;

                    if (dept != null){//经费指标账中存在单位信息
                        if ("92#".equals(jypz.getYoupin())){
                            dept.setZhichuleixing92((dept.getZhichuleixing92() ==null? 0:dept.getZhichuleixing92()) +bencijine ) ;
                        }
                        if ("95#".equals(jypz.getYoupin())){
                            dept.setZhichuleixing95((dept.getZhichuleixing95() ==null? 0:dept.getZhichuleixing95()) +bencijine ) ;
                        }
                        if ("-35#".equals(jypz.getYoupin())){
                            dept.setZhichuleixing35((dept.getZhichuleixing35() ==null? 0:dept.getZhichuleixing35()) +bencijine ) ;
                        }
                        dept.setUnitinformation((dept.getUnitinformation() ==null ? 0:dept.getUnitinformation())  - bencijine) ;
                        shhYljfzbzMapper.updateById(dept) ;
                    }
                }


            }
        }

        return this.updateById(entity);
    }

    @Override
    public List<TShhGldjb> getExportDataList(TShhGldjbParam param) {
        return mapper.getExportDataList(param);
    }
}
