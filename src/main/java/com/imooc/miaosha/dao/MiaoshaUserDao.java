package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MiaoshaUserDao {

    /*
    根据id获取用户对象
    * */
    @Select("select*from miaosha_user where id=#{id}")
    MiaoshaUser getById(@Param("id") long id);

}
