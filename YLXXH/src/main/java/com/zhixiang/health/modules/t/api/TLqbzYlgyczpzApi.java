package com.zhixiang.health.modules.t.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyczpzDto;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzYlgyczpz;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyczpzParam;
import com.zhixiang.health.modules.t.model.param.TLqbzYlgyzbzParam;
import com.zhixiang.health.modules.t.service.ITLqbzYlgyczpzService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 油料供应充值凭证 前端控制器
 * </p>
 *
 * @author Dingx
 * @since 2021-06-18
 */
@RestController
@RequestMapping("/t/lqbz/ylgyczpz")
public class TLqbzYlgyczpzApi extends BaseHttp {

    @Value("${ylxxh.lqbz.file.jygl}")
    private String filePath;

    @Resource
    private ITLqbzYlgyczpzService service;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TLqbzYlgyczpz> page(Page<TLqbzYlgyczpz> page, TLqbzYlgyczpz entity) {
        return new HttpPageResult<>(service.page(page));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody TLqbzYlgyczpz entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TLqbzYlgyczpz entity ){
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
    public HttpResult<TLqbzYlgyczpz> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/sjdwSave")
    public HttpResult<Boolean> sjdwSave(@RequestBody TLqbzYlgyczpz entity, UserParam param){
        return new HttpResult<>(service.sjdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/sjdwUpdate")
    public HttpResult<Boolean> sjdwUpdate( @RequestBody TLqbzYlgyczpz entity, UserParam param ){
        return new HttpResult<>(service.sjdwUpdate(entity,param));
    }

    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageList")
    public HttpPageResult<TLqbzYlgyczpzDto> pageList(TLqbzYlgyczpzParam param) {
        return new HttpPageResult<>(service.pageList(param));
    }

    /**
     * 获取上一次公里数
     * @return Boolean
     */
    @GetMapping(value = "/getLastCzpzBycarnum")
    public HttpResult<TLqbzYlgyczpzDto> getLastCzpzBycarnum( TLqbzYlgyczpzParam param ){
        return new HttpResult<>(service.getLastCzpzBycarnum(param));
    }

    /**
     * 获取上一次公里数
     * @return Boolean
     */
    @GetMapping(value = "/getJypzById")
    public HttpResult<TLqbzYlgyczpzDto> getJypzById( TLqbzYlgyczpzParam param ){
        return new HttpResult<>(service.getJypzById(param));
    }

    /**
     * 保存到指标账
     * @return Boolean
     */
    @GetMapping(value = "/saveTozbz")
    public HttpResult<Boolean> saveTozbz( TLqbzYlgyczpzParam param ){
        return new HttpResult<>(service.saveTozbz(param));
    }

    /**
     * 获取充值凭证列表
     * @return Boolean
     */
    @GetMapping(value = "/getCzpz")
    public HttpResult<List<TLqbzYlgyczpzDto>> getCzpz(TLqbzYlgyczpzParam param ){
        return new HttpResult<>(service.getCzpz(param));
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
    public HttpResult<String> importData(@RequestBody TLqbzYlgyczpzParam param, UserParam userParam) {

        return service.importData(param,userParam).convert();
    }
}

