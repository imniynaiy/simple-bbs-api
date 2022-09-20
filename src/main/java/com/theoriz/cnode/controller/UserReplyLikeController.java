package com.theoriz.cnode.controller;

import com.theoriz.cnode.config.CnodeUserDetails;
import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.model.TopicDetailVo;
import com.theoriz.cnode.service.IUserReplyLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8020")
public class UserReplyLikeController {
    @Autowired
    private IUserReplyLikeService userReplyLikeService;

    @PostMapping("/reply/{replyId}/ups")
    public Result upOrDownReply(@PathVariable Long replyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CnodeUserDetails) {
            CnodeUserDetails cnodeUserDetails = (CnodeUserDetails) authentication.getPrincipal();
            String action = userReplyLikeService.upOrDownReply(replyId, cnodeUserDetails.getId());
            if (action == null) {
                return new Result(false);
            }
            Map<String, String> data = new HashMap();
            data.put("action", action);
            return new Result(true, data);
        }
        return new Result(false);
    }
}
