package com.theoriz.cnode.mapper;

import com.theoriz.cnode.domain.entity.Topic;
import com.theoriz.cnode.domain.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Arrays;
import java.util.List;

public interface TopicCollectMapper {

    @Select("select exists(select * from tb_user_topic_collect where topic_id = #{topicId} and user_id = #{userId})")
    boolean exist(Long topicId, Long userId);

    @Insert("insert into tb_user_topic_collect(user_id, topic_id) values(#{userId}, #{topicId})")
    int add(Long topicId, Long userId);


    @Delete("delete from tb_user_topic_collect where topic_id = #{topicId} and user_id = #{userId}")
    int remove(Long topicId, Long userId);

    @Select("select t.* from tb_user_topic_collect c inner join tb_topic t where t.id = c.topic_id and c.user_id = #{userId}")
    @Results({
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "last_reply_at", property = "lastReplyAt"),
            @Result(column = "reply_count", property = "replyCount"),
            @Result(column = "visit_count", property = "visitCount"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt"),
            @Result(
                    property = "author",
                    javaType = User.class,
                    column = "author_id",
                    one = @One(select = "com.theoriz.cnode.mapper.UserMapper.selectById")
            )
    })
    List<Topic> findByUserId(Long userId);
}
