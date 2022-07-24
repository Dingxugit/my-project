package com.zhixiang.health.modules.t.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.abs.BaseHttp;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.modules.sys.service.ISysDeptService;
import com.zhixiang.health.modules.t.model.dto.TCarDto;
import com.zhixiang.health.modules.t.model.entity.TCar;
import com.zhixiang.health.modules.t.model.param.TCarParam;
import com.zhixiang.health.modules.t.model.param.TLqbzDcyhkpParam;
import com.zhixiang.health.modules.t.service.ITCarService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @since 2021-06-01
 */
@RestController
@RequestMapping("/t/car")
public class TCarApi extends BaseHttp {

    @Resource
    private ITCarService service;

    @Resource
    private ISysDeptService sysDeptService ;

    @Value("${ylxxh.carInfo.file.cars}")
    private String filePath;

    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TCarDto> page(TCarParam param) {

        return new HttpPageResult<>(service.pageList(param));
    }


    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save(@RequestBody TCar entity, UserParam param){
        entity.setUnitId(Integer.valueOf(param.getFirstDeptId()) ) ;
        entity.setUnitName(sysDeptService.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        entity.setIsdc("N" );
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TCar entity, UserParam param ){
        entity.setUnitId(Integer.valueOf(param.getFirstDeptId()) ) ;
        entity.setUnitName(sysDeptService.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        return new HttpResult<>(service.updateById(entity));
    }

    /**
    * 删除实体信息
    * @param id 实体ID
    * @return Boolean
    */
    @DeleteMapping(value = "/remove/{id}")
    public HttpResult<Boolean> removeById( @PathVariable Integer id ){
        return new HttpResult<>(service.removeById(id));
    }

    /**
    * 查找实体信息
    * @param id 实体ID
    * @return HttpResult
    */
    @GetMapping(value = "/get/{id}")
    public HttpResult<TCar> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }


    /**
     * 获取车辆信息
     * @param param
     * @return
     */
    @GetMapping(value = "/getCarInfo")
    public HttpResult<List<TCarDto>> getCarInfo(@RequestParam("deptname") String deptname, UserParam param){

        if(StringUtils.isNotBlank(deptname)){
            return new HttpResult<>(service.getCarInfoByDeptname(deptname));
        }
        return new HttpResult<>(service.getCarInfo(param.getFirstDeptId()));
    }

    /**
     * 获取车辆信息
     * @param param
     * @return
     */
    @GetMapping(value = "/getCarList")
    public HttpResult<List<TCarDto>> getCarList(UserParam param){
        return new HttpResult<>(service.getCarList(param.getFirstDeptId()));
    }

    /**
     * 上传excel文件
     * @param uploadFile 文件流
     * @return 文件存储路径
     */
    @PostMapping("/uploadFile")
    public HttpResult<String> uploadFile(@RequestParam(value = "file") MultipartFile uploadFile ){
        String fileUrl = FileUtil.uploadFile(uploadFile, filePath);
        return new HttpResult<>(fileUrl).setIsSuccess(StrUtil.isNotEmpty(fileUrl));
    }

    /**
     * 导入数据
     * @param param excel文件路径、机构编码
     * @param userParam userParam
     * @return 导入数据结果
     */
    @PostMapping(value = "/importData")
    public HttpResult<String> importData(@RequestBody TCarParam param, UserParam userParam) {

        return service.importData(param,userParam).convert();
    }

    /**
     * 获取车辆信息
     * @param param
     * @return
     */
    @GetMapping(value = "/getCarInfoBynum")
    public HttpResult<TCarDto> getCarInfoBynum(TCarParam param){
        return new HttpResult<>(service.getCarInfoBynum(param));
    }

}

