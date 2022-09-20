package com.theoriz.cnode.mapper;

import com.theoriz.cnode.domain.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Insert("INSERT INTO tb_user(loginname, password) VALUES (#{loginname},#{password})")
    @SelectKey(statement= "select last_insert_id()", keyProperty="id", before=false, resultType=Long.class)
    int save(User user);

    @Select("SELECT * FROM tb_user WHERE loginname = #{loginname} limit 1")
    @Results({
            @Result(column = "avatar_url", property = "avatarUrl"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt")
    })
    User findByLoginname(String loginname);

    @Select("select * from tb_user where id = #{id} limit 1")
    @Results({
            @Result(column = "avatar_url", property = "avatarUrl"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt")
    })
    User selectById(Long userId, Long id);

    @Update("update tb_user set score = score + #{toAdd} where id = #{userId}")
    int addScore(Long userId, int toAdd);
}
