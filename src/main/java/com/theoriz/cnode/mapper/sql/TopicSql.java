package com.theoriz.cnode.mapper.sql;

import com.theoriz.cnode.domain.entity.Topic;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

public class TopicSql {
    public String getUpdate(Topic topic){
        return new SQL() {
            {
                UPDATE("tb_topic");
                if (StringUtils.hasLength(topic.getTitle())){
                    SET("title = #{title}");
                }
                if (StringUtils.hasLength(topic.getContent())){
                    SET("content = #{content}");
                }
                if (StringUtils.hasLength(topic.getTab())){
                    SET("tab = #{tab}");
                }
                WHERE("id = #{id}");
                if (topic.getAuthorId() != null) {
                    WHERE("author_id = #{authorId}");
                }
                LIMIT(1);
            }
        }.toString();
    }

    public String list(String tab, boolean good) {
        return new SQL() {
            {
                SELECT("*");
                FROM("tb_topic");
                if (StringUtils.hasText(tab)) {
                    WHERE("tab = #{tab}");
                }
                if (good) {
                    WHERE("good = 1");
                }
                ORDER_BY("top desc");
                ORDER_BY("last_reply_at desc");
                ORDER_BY("created_at desc");
            }
        }.toString();
    }
}
