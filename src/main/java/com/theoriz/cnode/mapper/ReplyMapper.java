package com.theoriz.cnode.mapper;

import com.theoriz.cnode.domain.entity.Reply;
import com.theoriz.cnode.domain.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ReplyMapper {
    @Select("Select * from tb_reply where user_id = #{userId} order by created_time desc limit #{limit}")
    List<Reply> getRecentRepliesByUserId(Long userId, int limit);

    @Insert("insert into tb_reply(topic_id,author_id,reply_id,content) values(#{topicId}, #{authorId}, #{replyId}, #{content})")
    @SelectKey(statement= "select last_insert_id()", keyProperty="id", before=false, resultType=Long.class)
    int save(Reply reply);

    @Select("select * from tb_reply where topic_id = #{topicId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "topic_id", property = "topicId"),
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "reply_id", property = "replyId"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(
                    property = "author",
                    javaType = User.class,
                    column = "author_id",
                    one = @One(select = "com.theoriz.cnode.mapper.UserMapper.selectById")
            ),
            @Result(
                    property = "upUserIdList",
                    javaType = List.class,
                    column = "id",
                    many = @Many(select = "com.theoriz.cnode.mapper.UserReplyLikeMapper.getUserIdsByReplyId")
            )
    })
    List<Reply> selectByTopicId(Long topicId);

    @Select("select exists(select * from tb_reply where id = #{replyId} and topic_id = #{topicId})")
    boolean replyExists(Long replyId, Long topicId);

    @Select("select * from tb_reply where id = #{id}")
    Reply getReply(Long id);
}
