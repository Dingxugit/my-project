package com.zhixiang.health.modules.t.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.FileUtil;
import com.zhixiang.health.modules.t.model.dto.TLqbzJykxxtjDto;
import com.zhixiang.health.modules.t.model.dto.TShhJytjDto;
import com.zhixiang.health.modules.t.model.entity.TLqbzJykxxtj;
import com.zhixiang.health.modules.t.model.entity.TShhJytj;
import com.zhixiang.health.modules.t.model.param.TLqbzJykxxtjParam;
import com.zhixiang.health.modules.t.model.param.TShhJytjParam;
import com.zhixiang.health.modules.t.service.ITShhJytjService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * <p>
 * 加油卡信息统计 前端控制器
 * </p>
 *
 * @author Dingx
 * @since 2021-06-20
 */
@RestController
@RequestMapping("/t/shh/jytj")
public class TShhJytjApi extends BaseHttp {

    @Value("${ylxxh.car.photo.ylshh}")
    private String photoPath;

    @Value("${ylxxh.ylshh.file.yljfzbz}")
    private String filePath;

    @Resource
    private ITShhJytjService service;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @GetMapping(value = "/page")
    public HttpPageResult<TShhJytj> page(Page<TShhJytj> page, TShhJytj entity) {
        return new HttpPageResult<>(service.page(page));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save(@RequestBody TShhJytj entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody TShhJytj entity ){
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
    public HttpResult<TShhJytj> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PostMapping(value = "/sjdwSave")
    public HttpResult<Boolean> sjdwSave(@RequestBody TShhJytj entity, UserParam param){
//        String photo = "";
//        if (!"".equals(entity.getPhoto()) && null !=entity.getPhoto()){
//            photo = entity.getPhoto().substring(entity.getPhoto().lastIndexOf("/"),entity.getPhoto().length()) ;
//            File file = new File(photoPath+photo);
//            String newName = entity.getNumberplate() + photo.substring(photo.indexOf("."),photo.length()) ;
//            if (file.exists()){
//                file.renameTo(new File(photoPath + newName)) ;
//            }
//            photo = entity.getPhoto().substring(0,entity.getPhoto().lastIndexOf("/")+1) ;
//            photo += newName ;
//            entity.setPhoto(photo) ;
//        }
        return new HttpResult<>(service.sjdwSave(entity,param));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @PutMapping(value = "/sjdwUpdate")
    public HttpResult<Boolean> sjdwUpdate( @RequestBody TShhJytj entity ){
//        String photo = "";
//        if (!"".equals(entity.getPhoto())&&  null !=entity.getPhoto()){
//            photo = entity.getPhoto().substring(entity.getPhoto().lastIndexOf("/"),entity.getPhoto().length()) ;
//            File file = new File(photoPath+photo);
//            String newName = entity.getNumberplate() + photo.substring(photo.indexOf("."),photo.length()) ;
//            if (file.exists()){
//                file.renameTo(new File(photoPath + newName)) ;
//            }
//            photo = entity.getPhoto().substring(0,entity.getPhoto().lastIndexOf("/")+1) ;
//            photo += newName ;
//            entity.setPhoto(photo) ;
//        }
        return new HttpResult<>(service.updateById(entity));
    }

    /**
     * 分页查询信息
     * @return page
     */
    @GetMapping(value = "/pageList")
    public HttpPageResult<TShhJytjDto> pageList(TShhJytjParam param) {
        return new HttpPageResult<>(service.pageList(param));
    }

    /**
     * 上传车辆图片
     * @param uploadFile 文件信息
     * @return 头像链接地址
     */
    @PostMapping("/uploadPhoto")
    public HttpResult<String> uploadPhoto(@RequestParam(value = "file") MultipartFile uploadFile ){
        String fileUrl = FileUtil.uploadFile(uploadFile, photoPath);
        fileUrl= fileUrl.substring(fileUrl.lastIndexOf("/") + 1,fileUrl.length()) ;///carPic
        return new HttpResult<>(fileUrl).setIsSuccess(StrUtil.isNotEmpty(fileUrl));
    }

    @GetMapping(value = "/getJykxxtjList")
    public HttpResult<List<TShhJytjDto>> getJykxxtjList(TShhJytjParam param) {
        return new HttpResult<>(service.getJykxxtjList(param));
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
    public HttpResult<String> importData(@RequestBody TShhJytjParam param, UserParam userParam) {
        return service.importData(param,userParam).convert();
    }
}

