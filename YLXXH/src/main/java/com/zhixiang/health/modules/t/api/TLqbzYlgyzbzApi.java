package com.zhixiang.health.modules.t.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.modules.sys.service.ISysDeptService;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyzbzParam;
import com.zhixiang.health.modules.t.service.ITLqbzYlgyzbzService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 油料供应指标账 前端控制器
 * </p>
 *
 * @author Dingx
 * @since 2021-06-15
 */
@RestController
@RequestMapping("/t/lqbz/ylgyzbz")
public class TLqbzYlgyzbzApi extends BaseHttp {

    @Value("${ylxxh.lqbz.file.ylgyzbz}")
    private String filePath;

    @Resource
    private ITLqbzYlgyzbzService service;

    @Resource
    private ISysDeptService deptService ;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TLqbzYlgyzbz> page(Page<TLqbzYlgyzbz> page, TLqbzYlgyzbz entity) {
        return new HttpPageResult<>(service.page(page));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save(@RequestBody TLqbzYlgyzbz entity , UserParam param){
        if (entity.getType() == 1){
            entity.setNumberplate(deptService.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        }
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TLqbzYlgyzbz entity , UserParam param){
        if (entity.getType() == 1){
            entity.setNumberplate(deptService.getSysDept(Integer.valueOf(param.getFirstDeptId())).getName()) ;
        }
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
    public HttpResult<TLqbzYlgyzbz> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }


    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageList")
    public HttpPageResult<TLqbzYlgyzbzDto> pageList(TLqbzYlgyzbzParam param) {
        return new HttpPageResult<>(service.pageList(param));
    }

    /**
     * 保存实体信息
     * 三级单位新增
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/sjdwSave")
    public HttpResult<Boolean> sjdwSave(@RequestBody TLqbzYlgyzbz entity , UserParam param){

        return new HttpResult<>(service.sjdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * 三级单位修改
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/sjdwUpdate")
    public HttpResult<Boolean> sjdwUpdate( @RequestBody TLqbzYlgyzbz entity , UserParam param){

        return new HttpResult<>(service.sjdwUpdate(entity,param));
    }

    /**
     * 获取单位指标账
     * @return List
     */
    @GetMapping(value = "/getDeptZbz")
    public HttpResult<List<TLqbzYlgyzbzDto>> getDeptZbz(TLqbzYlgyzbzParam param,UserParam param1 ){
        return new HttpResult<>(service.getDeptZbz(param,param1));
    }

    /**
     * 保存实体信息
     * 二级单位新增
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/ejdwSave")
    public HttpResult<Boolean> ejdwSave(@RequestBody TLqbzYlgyzbz entity , UserParam param){

        return new HttpResult<>(service.ejdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * 二级单位修改
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/ejdwUpdate")
    public HttpResult<Boolean> ejdwUpdate( @RequestBody TLqbzYlgyzbz entity , UserParam param){

        return new HttpResult<>(service.ejdwUpdate(entity,param));
    }

    /**
     * 二级单位查询信息
     * @return page
     */
    @GetMapping(value = "/pageEjdw")
    public HttpPageResult<TLqbzYlgyzbzDto> pageEjdw(TLqbzYlgyzbzParam param) {
        return new HttpPageResult<>(service.pageEjdw(param));
    }

    /**
     * 上传excel文件
     * @param uploadFile 文件流
     * @return 文件存储路径
     */
    @PostMapping("/uploadFile")
    public HttpResult<String> uploadPhoto(@RequestParam(value = "file") MultipartFile uploadFile ){
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
    public HttpResult<String> importData(@RequestBody TLqbzYlgyzbzParam param, UserParam userParam) {

        return service.importData(param,userParam).convert();
    }

    /**
     * 二级单位月度报表分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageEjdwYdbb")
    public HttpPageResult<TLqbzYlgyzbzDto> pageEjdwYdbb(TLqbzYlgyzbzParam param) {
        return new HttpPageResult<>(service.pageEjdwYdbb(param));
    }

    /**
     * 是否存在记录
     * @return Boolean
     */
    @GetMapping(value = "/searchExists")
    public HttpResult<Boolean> searchExists(TLqbzYlgyzbzParam param){

        return new HttpResult<>(service.searchExists(param));
    }

}

