package com.theoriz.cnode.controller;

import com.theoriz.cnode.config.CnodeUserDetails;
import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.domain.model.*;
import com.theoriz.cnode.service.ITopicCollectService;
import com.theoriz.cnode.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicCollectController {
    @Autowired
    private ITopicCollectService topicCollectService;
    @Autowired
    private IUserService userService;

    @PostMapping("/topic_collect/collect")
    public Result collectTopic(@RequestBody TopicCollectDto topicCollectDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TopicDetailVo data = new TopicDetailVo();
        if (authentication.getPrincipal() instanceof CnodeUserDetails) {
            CnodeUserDetails cnodeUserDetails = (CnodeUserDetails) authentication.getPrincipal();
            boolean success = topicCollectService.add(topicCollectDto.getTopicId(), cnodeUserDetails.getId());
            return new Result(success);
        }
        return new Result(false);
    }

    @PostMapping("/topic_collect/de_collect")
    public Result deCollectTopic(@RequestBody TopicCollectDto topicCollectDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TopicDetailVo data = new TopicDetailVo();
        if (authentication.getPrincipal() instanceof CnodeUserDetails) {
            CnodeUserDetails cnodeUserDetails = (CnodeUserDetails) authentication.getPrincipal();
            boolean success = topicCollectService.remove(topicCollectDto.getTopicId(), cnodeUserDetails.getId());
            return new Result(success);
        }
        return new Result(false);
    }

    @GetMapping("/topic_collect/{loginName}")
    public Result getCollectTopics(@PathVariable String loginName){
        User user = userService.getUserByLoginName(loginName);
        if (user != null) {
            List<TopicVo> topics = topicCollectService.findByUserId(user.getId());
            return new Result(true, topics);
        }
        return new Result(false);
    }
}
