package com.theoriz.cnode.mapper;

import com.theoriz.cnode.domain.entity.Topic;
import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.mapper.sql.TopicSql;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface TopicMapper {
    @Select("select * from tb_topic where author_id = #{userId} order by created_at desc limit #{limit}")
    List<Topic> getRecentTopicsByUserId(Long userId, int limit);

    @Select("SELECT DISTINCT t.* FROM tb_topic t LEFT JOIN tb_reply r ON t.id = r.topic_id WHERE r.author_id = #{userId} ORDER BY created_at DESC LIMIT #{limit}")
    List<Topic> getRecentReplyTopicsByUserId(Long userId, int limit);

    @Insert("insert into tb_topic(author_id,tab,content,title,last_reply_at,good,top,reply_count,visit_count) values(#{authorId}, #{tab}, #{content}, #{title}, #{lastReplyAt}, #{good}, #{top}, #{replyCount}, #{visitCount})")
    @SelectKey(statement= "select last_insert_id()", keyProperty="id", before=false, resultType=Long.class)
    int save(Topic topic);

    @Update("update tb_topic set last_reply_at=now() where id = #{topicId} ")
    void updateReplyTime(Long topicId);

    @Select("select exists(select * from tb_topic where id = #{id})")
    boolean topicExists(Long id);

    @SelectProvider(type = TopicSql.class, method = "list")
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
    List<Topic> list(String tab, boolean good);

    @Select("select * from tb_topic where id = #{id} limit 1")
    @Results({
            @Result(column = "id", property = "id"),
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
            ),
            @Result(
                    property = "replyList",
                    javaType = List.class,
                    column = "id",
                    many = @Many(select = "com.theoriz.cnode.mapper.ReplyMapper.selectByTopicId")
            )
    })
    Topic getByIdWithReplies(Long id);


//    @Update("update tb_topic set(tab, comment, title) values(#{tab}, #{comment}, #{title}) where author_id=#{authorId} and id = #{id}")
    @UpdateProvider(type = TopicSql.class, method = "getUpdate")
    int update(Topic topic);

    @Select("select * from tb_topic where id = #{id} limit 1")
    @Results({
            @Result(column = "author_id", property = "authorId"),
            @Result(column = "last_reply_at", property = "lastReplyAt"),
            @Result(column = "reply_count", property = "replyCount"),
            @Result(column = "visit_count", property = "visitCount"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt")
    })
    Topic getById(Long id);

    @Update("update tb_topic set reply_count = reply_count + #{i} where id = #{id}")
    void addReplyCount(int i, Long id);

    @Update("update tb_topic set visit_count = visit_count + #{i} where id = #{id}")
    void addVisitCount(int i, Long id);
}
