package com.zhixiang.health.modules.t.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.dto.TShhYljfzbzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyzbz;
import com.zhixiang.health.modules.t.model.entity.TShhYljfzbz;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyzbzParam;
import com.zhixiang.health.modules.t.model.param.TShhYljfzbzParam;
import com.zhixiang.health.modules.t.service.ITShhYljfzbzService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 2021年油料经费指标账 前端控制器
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@RestController
@RequestMapping("/t/shh/yljfzbz")
public class TShhYljfzbzApi extends BaseHttp {

    @Value("${ylxxh.ylshh.file.yljfzbz}")
    private String filePath;

    @Resource
    private ITShhYljfzbzService service;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TShhYljfzbz> page(Page<TShhYljfzbz> page, TShhYljfzbz entity) {
        return new HttpPageResult<>(service.page(page));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody TShhYljfzbz entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TShhYljfzbz entity ){
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
    public HttpResult<TShhYljfzbz> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageList")
    public HttpPageResult<TShhYljfzbzDto> pageList(TShhYljfzbzParam param) {
        return new HttpPageResult<>(service.pageList(param));
    }

    /**
     * 保存实体信息
     * 三级单位新增
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/sjdwSave")
    public HttpResult<Boolean> sjdwSave(@RequestBody TShhYljfzbz entity , UserParam param){

        return new HttpResult<>(service.sjdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * 三级单位修改
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/sjdwUpdate")
    public HttpResult<Boolean> sjdwUpdate( @RequestBody TShhYljfzbz entity , UserParam param){

        return new HttpResult<>(service.sjdwUpdate(entity,param));
    }

    /**
     * 获取单位指标账
     * @return List
     */
    @GetMapping(value = "/getDeptZbz")
    public HttpResult<List<TShhYljfzbzDto>> getDeptZbz(TShhYljfzbzParam param, UserParam param1 ){
        return new HttpResult<>(service.getDeptZbz(param,param1));
    }

    /**
     * 二级单位查询信息
     * @return page
     */
    @GetMapping(value = "/pageEjdw")
    public HttpPageResult<TShhYljfzbzDto> pageEjdw(TShhYljfzbzParam param) {
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
    public HttpResult<String> importData(@RequestBody TShhYljfzbzParam param, UserParam userParam) {

        return service.importData(param,userParam).convert();
    }

    /**
     * 保存实体信息
     * 二级单位新增
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/ejdwSave")
    public HttpResult<Boolean> ejdwSave(@RequestBody TShhYljfzbz entity , UserParam param){

        return new HttpResult<>(service.ejdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * 二级单位修改
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/ejdwUpdate")
    public HttpResult<Boolean> ejdwUpdate( @RequestBody TShhYljfzbz entity , UserParam param){

        return new HttpResult<>(service.ejdwUpdate(entity,param));
    }

    /**
     * 二级单位月度报表分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageEjdwYdbb")
    public HttpPageResult<TShhYljfzbzDto> pageEjdwYdbb(TShhYljfzbzParam param) {
        return new HttpPageResult<>(service.pageEjdwYdbb(param));
    }

    /**
     * 是否存在记录
     * @return Boolean
     */
    @GetMapping(value = "/searchExists")
    public HttpResult<Boolean> searchExists(TShhYljfzbzParam param){

        return new HttpResult<>(service.searchExists(param));
    }
}

