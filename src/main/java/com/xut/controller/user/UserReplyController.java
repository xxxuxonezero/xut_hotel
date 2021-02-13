package com.xut.controller.user;

import com.xut.bean.Reply;
import com.xut.controller.auth.AuthUtil;
import com.xut.filter.Identity;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import com.xut.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user/reply")
public class UserReplyController {
    @Autowired
    ReplyService replyService;

    @PostMapping("/create")
    public NoneDataResult create(@RequestBody Reply reply, HttpServletRequest request) {
        NoneDataResult result = new NoneDataResult();
        Identity identity = AuthUtil.getIdentity(request);
        if (identity == null) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        reply.setUserId(identity.getUserId());
        return replyService.create(reply);
    }

    @PostMapping("/delete")
    public NoneDataResult delete(@RequestParam("id") Integer id, HttpServletRequest request) {
        NoneDataResult result = new NoneDataResult();
        Identity identity = AuthUtil.getIdentity(request);
        if (identity == null) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        Result<Reply> replyResult = replyService.getById(id);
        if (replyResult.isNotValid()) {
            result.setCode(replyResult.getCode());
            return result;
        }
        if (replyResult.getData().getUserId().equals(identity.getUserId())) {
            result = replyService.delete(id, null);
        } else {
            result.setCode(Code.NO_AUTH);
        }
        return result;
    }
}
