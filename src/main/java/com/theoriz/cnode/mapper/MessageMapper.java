package com.theoriz.cnode.mapper;

import com.theoriz.cnode.domain.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MessageMapper {
    @Select("select count(*) from tb_message where user_id = #{userId} and has_read = 0")
    int getCountByUserId(Long userId);

    @Select("select * from tb_message where user_id = #{userId}")
    @Results({
            @Result(column = "has_read", property = "hasRead"),
            @Result(column = "topic_id", property = "topicId"),
            @Result(column = "reply_id", property = "replyId"),
            @Result(column = "created_at", property = "createdAt"),
            @Result(column = "updated_at", property = "updatedAt")
    })
    List<Message> getMessagesByUserId(Long userId);

    @Insert("insert into tb_message(user_id,type,has_read,topic_id,reply_id,content) values(#{userId}, #{type}, #{hasRead}, #{topicId}, #{replyId}, #{content})")
    void addMessage(Long userId, String type, int hasRead, Long topicId, Long replyId, String content);

    @Select("select id from tb_message where user_id = #{userId} and has_read = 0")
    List<Long> getUnreadMessageIdsByUserId(Long userId);

    @Update("<script>" +
            "update tb_message set has_read=1 where id in " +
            "<foreach collection='affectedMessageIds' open='(' item='affectedMessageId' separator=',' close=')'>" +
            "#{affectedMessageId}</foreach>" +
            "</script>")
    void markMessagesRead(List<Long> affectedMessageIds);

    @Update("update tb_message set has_read=1 where user_id=#{userId} and id=#{messageId}")
    int markOneMessageRead(Long userId, Long messageId);
}
