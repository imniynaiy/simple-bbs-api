package com.theoriz.cnode.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserReplyLikeMapper {
    @Select("select user_id from tb_user_reply_like where reply_id = #{replyId}")
    List<Long> getUserIdsByReplyId(Long replyId);

    @Select("select exists (select * from tb_user_reply_like where reply_id=#{replyId})")
    boolean exists(Long userId, Long replyId);

    @Delete("delete from tb_user_reply_like where user_id = #{userId} and reply_id = #{replyId}")
    int delete(Long userId, Long replyId);

    @Insert("insert into tb_user_reply_like(user_id, reply_id) values(#{userId},#{replyId})")
    int add(Long userId, Long replyId);
}
