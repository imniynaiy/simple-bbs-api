 package com.theoriz.cnode.controller;

import com.theoriz.cnode.config.CnodeUserDetails;
import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.model.MessageVo;
import com.theoriz.cnode.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8020")
public class MessageController {
    @Autowired
    private IMessageService messageService;

    @GetMapping("/message/count")
    public Result getCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CnodeUserDetails details = (CnodeUserDetails) authentication.getPrincipal();
        Integer result = messageService.getCountByUserId(details.getId());

        if (result != null) {

            return new Result(true, result);
        }
        return new Result(false);
    }

    @GetMapping("/messages")
    public Result getMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CnodeUserDetails details = (CnodeUserDetails) authentication.getPrincipal();
        Map<String, List<MessageVo>> data = new HashMap<>();
        data = messageService.getMessagesByUserId(details.getId());
        return new Result(true, data);
    }

    @PostMapping("/message/mark_all")
    public Result markAllMessageRead() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CnodeUserDetails details = (CnodeUserDetails) authentication.getPrincipal();
        List<Long> markedMessageIds = messageService.markAllMessageRead(details.getId());
        Map<String, List<Long>> data = new HashMap<>();
        data.put("marked_msgs", markedMessageIds);
        return new Result(true, data);
    }

    @PostMapping("/message/mark_one/{messageId}")
    public Result markOneMessageRead(@PathVariable Long messageId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CnodeUserDetails details = (CnodeUserDetails) authentication.getPrincipal();
        boolean success = messageService.markOneMessageRead(details.getId(), messageId);
        if (success) {
            return new Result(true, messageId);
        }
        return new Result(false);
    }
}

