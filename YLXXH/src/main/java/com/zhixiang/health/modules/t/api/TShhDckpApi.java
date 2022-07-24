package com.zhixiang.health.modules.t.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.modules.t.model.dto.TLqbzDcyhkpDto;
import com.zhixiang.health.modules.t.model.dto.TShhDckpDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzDcyhkp;
import com.zhixiang.health.modules.t.model.entity.TShhDckp;
import com.zhixiang.health.modules.t.model.param.TLqbzDcyhkpParam;
import com.zhixiang.health.modules.t.model.param.TShhDckpParam;
import com.zhixiang.health.modules.t.service.ITShhDckpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 油料社会化保障车辆单车耗油考核卡片 前端控制器
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@RestController
@RequestMapping("/t/shh/dckp")
public class TShhDckpApi extends BaseHttp {

    @Value("${ylxxh.ylshh.file.jykgl}")
    private String filePath;

    @Resource
    private ITShhDckpService service;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TShhDckp> page(Page<TShhDckp> page, TShhDckp entity) {
        return new HttpPageResult<>(service.page(page));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody TShhDckp entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TShhDckp entity ){
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
    public HttpResult<TShhDckp> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/sjdwSave")
    public HttpResult<Boolean> sjdwSave(@RequestBody TShhDckp entity, UserParam param){
        return new HttpResult<>(service.sjdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/sjdwUpdate")
    public HttpResult<Boolean> sjdwUpdate( @RequestBody TShhDckp entity , UserParam param){
        return new HttpResult<>(service.sjdwUpdate(entity,param));
    }

    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageList")
    public HttpPageResult<TShhDckpDto> pageList(TShhDckpParam param) {
        return new HttpPageResult<>(service.pageList(param));
    }

    /**
     * 获取上一次公里数
     * @return Boolean
     */
    @GetMapping(value = "/getLastGlsBycarnum")
    public HttpResult<TShhDckpDto> getLastGlsBycarnum(TShhDckpParam param ){
        return new HttpResult<>(service.getLastGlsBycarnum(param));
    }

    /**
     * 获取单车油耗列表
     * @return Boolean
     */
    @GetMapping(value = "/getDcyhList")
    public HttpResult<List<TShhDckpDto>> getDcyhList(TShhDckpParam param ){
        return new HttpResult<>(service.getDcyhList(param));
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
    public HttpResult<String> importData(@RequestBody TShhDckpParam param, UserParam userParam) {

        return service.importData(param,userParam).convert();
    }

    /**
     * 获取单车油耗信息
     * @return Boolean
     */
    @GetMapping(value = "/getDcyhxxBycarnum")
    public HttpResult<Map<String,Object>> getDcyhxxBycarnum(TShhDckpParam param ){
        return new HttpResult<>(service.getDcyhxxBycarnum(param));
    }

    /**
     * 获取单车油耗分析
     * @return Boolean
     */
    @GetMapping(value = "/pageEjdwYhfx")
    public HttpPageResult<TShhDckpDto> pageEjdwYhfx(TShhDckpParam param ) throws ParseException {
        return new HttpPageResult<>(service.pageEjdwYhfx(param));
    }
}

