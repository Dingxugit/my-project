package com.zhixiang.health.modules.t.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.modules.t.model.dto.TSwyDjDto;
import com.zhixiang.health.modules.t.model.dto.TSwySwylzDto;
import com.zhixiang.health.modules.t.model.entity.TSwyDj;
import com.zhixiang.health.modules.t.model.param.TSwyDcyhParam;
import com.zhixiang.health.modules.t.model.param.TSwyDjParam;
import com.zhixiang.health.modules.t.model.param.TSwySwylzParam;
import com.zhixiang.health.modules.t.service.ITSwyDjService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 价拨/实物油加注登记表 前端控制器
 * </p>
 *
 * @author Dingx
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/t/swy/dj")
public class TSwyDjApi extends BaseHttp {

    @Value("${ylxxh.swy.file.djb}")
    private String filePath;

    @Resource
    private ITSwyDjService service;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TSwyDj> page(Page<TSwyDj> page, TSwyDj entity) {
        return new HttpPageResult<>(service.page(page));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save(@RequestBody TSwyDj entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TSwyDj entity ){
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
    public HttpResult<TSwyDj> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageList")
    public HttpPageResult<TSwyDjDto> pageList(TSwyDjParam param) {
        return new HttpPageResult<>(service.pageList(param));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/sjdwSave")
    public HttpResult<Boolean> sjdwSave(@RequestBody TSwyDj entity , UserParam param){
        return new HttpResult<>(service.sjdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/sjdwUpdate")
    public HttpResult<Boolean> sjdwUpdate( @RequestBody TSwyDj entity , UserParam param){
        return new HttpResult<>(service.sjdwUpdate(entity,param));
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
    public HttpResult<String> importData(@RequestBody TSwyDjParam param, UserParam userParam) {

        return service.importData(param,userParam).convert();
    }
    /**
     * 获取导出信息
     * @return page
     */
    @GetMapping(value = "/getDjList")
    public HttpResult<List<TSwyDjDto>> getDjList(TSwyDjParam param) {
        return new HttpResult<>(service.getDjList(param));
    }

}

