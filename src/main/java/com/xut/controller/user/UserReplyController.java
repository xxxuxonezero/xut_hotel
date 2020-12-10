package com.xut.controller.user;

import com.xut.bean.Reply;
import com.xut.model.NoneDataResult;
import com.xut.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/reply")
public class UserReplyController {
    @Autowired
    ReplyService replyService;

    @PostMapping("/create")
    public NoneDataResult create(@RequestBody Reply reply) {
        return replyService.create(reply);
    }

    @PostMapping("/delete")
    public NoneDataResult delete(@RequestParam("id") Integer id) {
        return replyService.delete(id, null);
    }
}
