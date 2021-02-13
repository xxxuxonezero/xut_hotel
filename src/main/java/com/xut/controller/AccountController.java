package com.xut.controller;

import com.xut.bean.User;
import com.xut.controller.auth.AuthUtil;
import com.xut.filter.Identity;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import com.xut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    UserService userService;

    @PostMapping("/update")
    public NoneDataResult create(@RequestBody User user, HttpServletRequest request) {
        NoneDataResult result = new NoneDataResult();
        Identity identity = AuthUtil.getIdentity(request);
        if (identity == null) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        Result<List<User>> userResult = userService.getByIds(Collections.singletonList(identity.getUserId()));
        if (userResult.isNotValid()) {
            result.setCode(userResult.getCode());
            return result;
        }
        User originUser = userResult.getData().get(0);
        originUser.setSex(user.getSex());
        originUser.setAvatar(user.getAvatar());
        originUser.setIntroduction(user.getIntroduction());
        originUser.setRealName(user.getRealName());
        originUser.setUserName(user.getUserName());
        result = userService.update(originUser);
        return result;
    }

    @GetMapping("/getUserInfo")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Result<User> result = new Result<>();
        Identity identity = AuthUtil.getIdentity(request);
        if (identity == null) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        Result<List<User>> userResult = userService.getByIds(Collections.singletonList(identity.getUserId()));
        if (userResult.isNotValid()) {
            result.setCode(userResult.getCode());
            return result;
        }
        User user = userResult.getData().get(0);
        result.setData(user);
        return result;
    }

    @PostMapping("/resetPassword")
    public NoneDataResult resetPassword(HttpServletRequest request,
                                        @RequestParam("password") String password) {
        NoneDataResult result = new NoneDataResult();
        Identity identity = AuthUtil.getIdentity(request);
        if (identity == null) {
            result.setCode(Code.NO_AUTH);
            return result;
        }
        result = userService.resetPassword(password, identity.getUserId());
        return result;
    }
}
