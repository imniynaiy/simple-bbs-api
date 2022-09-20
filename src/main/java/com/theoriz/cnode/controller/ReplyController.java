package com.theoriz.cnode.controller;

import com.theoriz.cnode.config.CnodeUserDetails;
import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.entity.Reply;
import com.theoriz.cnode.domain.entity.Topic;
import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.domain.model.ReplyDto;
import com.theoriz.cnode.domain.model.TopicDto;
import com.theoriz.cnode.service.IReplyService;
import com.theoriz.cnode.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8020")
public class ReplyController {
    @Autowired
    private IReplyService replyService;
    @Autowired
    private ITopicService topicService;

    @PostMapping("/topic/{topicId}/replies")
    public Result addReply(ReplyDto replyDto, @PathVariable Long topicId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CnodeUserDetails details = (CnodeUserDetails) authentication.getPrincipal();

        Reply reply = new Reply();
        Long userId = details.getId();
        reply.setAuthorId(userId);
        reply.setAuthor(new User(userId, details.getUsername()));
        reply.setTopicId(topicId);
        reply.setContent(replyDto.getContent());
        reply.setReplyId(replyDto.getReplyId());
        Long replyId = replyService.save(reply);
        if (replyId != null) {
            Map<String, Long> data = new HashMap();
            data.put("reply_id", replyId);
            return new Result(true, data);
        }
        return new Result(false);
    }
}
