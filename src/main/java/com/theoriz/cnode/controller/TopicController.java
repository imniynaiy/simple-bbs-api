package com.theoriz.cnode.controller;

import com.theoriz.cnode.config.CnodeUserDetails;
import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.entity.Topic;
import com.theoriz.cnode.domain.model.ListTopicDto;
import com.theoriz.cnode.domain.model.TopicDetailVo;
import com.theoriz.cnode.domain.model.TopicDto;
import com.theoriz.cnode.domain.model.TopicVo;
import com.theoriz.cnode.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8020")
public class TopicController {
    @Autowired
    private ITopicService topicService;

    @PostMapping("/topics")
    public Result addTopic(TopicDto topicDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CnodeUserDetails details = (CnodeUserDetails) authentication.getPrincipal();
        Topic topic = new Topic();
        topic.setAuthorId(details.getId());
        topic.setContent(topicDto.getContent());
        topic.setTitle(topicDto.getTitle());
        topic.setTab(topicDto.getTab());
        Long topicId = topicService.save(topic);
        if (topicId != null) {
            Map<String, Long> data = new HashMap<>();
            data.put("topic_id", topicId);
            return new Result(true, data);
        }
        return new Result(false);
    }

    @GetMapping("/topics")
    public Result listTopics(ListTopicDto listTopicDto) {
        String tab = listTopicDto.getTab();
        if ("all".equals(tab)) {
            listTopicDto.setTab(null);
        } else if ("good".equals(tab)) {
            listTopicDto.setTab(null);
            listTopicDto.setGood(true);
        }
        List<TopicVo> topics = topicService.findPage(listTopicDto);
        return new Result(true, topics);
    }

    @GetMapping("/topic/{id}")
    public Result getTopic(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TopicDetailVo data;
        if (authentication.getPrincipal() instanceof CnodeUserDetails) {
            CnodeUserDetails cnodeUserDetails = (CnodeUserDetails) authentication.getPrincipal();
            data = topicService.getTopicDetail(id, cnodeUserDetails.getId());
        } else {
            data = topicService.getTopicDetail(id);
        }
        return new Result(true, data);
    }

    @PostMapping("/topics/update")
    public Result updateTopic(@RequestBody TopicDto topicDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CnodeUserDetails) {
            CnodeUserDetails cnodeUserDetails = (CnodeUserDetails) authentication.getPrincipal();
            Long authenticatedUserId = cnodeUserDetails.getId();
            if (topicService.update(topicDto, authenticatedUserId)){
                Map<String, Long> data = new HashMap<>();
                data.put("topic_id", topicDto.getId());
                return new Result(true, data);
            };
        }
        return new Result(false);
    }
}
