package com.theoriz.cnode.mapper;

import com.theoriz.cnode.domain.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

public interface UserMapper {

    @Insert("INSERT INTO tb_user(loginname, password) VALUES (#{loginname},#{password})")
    @SelectKey(statement= "select last_insert_id()", keyProperty="id", before=false, resultType=long.class)
    long save(User user);

    @Select("SELECT * FROM tb_user WHERE loginname = #{loginname}")
    User findByLoginname(String loginname);
}
