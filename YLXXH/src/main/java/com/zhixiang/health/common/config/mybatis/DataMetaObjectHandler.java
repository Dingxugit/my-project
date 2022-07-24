package com.zhixiang.health.common.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zhixiang.health.common.utils.UserUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @Description: mybatis 自动填充字段
 * @Auther: HeJiawang
 * @Date: 2020-06-02
 */
@Component
public class DataMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createBy = getFieldValByName("createBy", metaObject);
        if ( StringUtils.isEmpty(createBy) ) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                String userName = UserUtil.getUserName(request);

                setFieldValByName("createBy", userName, metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateBy = getFieldValByName("updateBy", metaObject);
        if ( StringUtils.isEmpty(updateBy) ) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (null != attributes) {
                HttpServletRequest request = attributes.getRequest();
                String userName = UserUtil.getUserName(request);

                setFieldValByName("updateBy", userName, metaObject);
                setFieldValByName("updateDate", new Date(), metaObject);
            }
        }
    }
}
