package com.xut.controller;

import com.xut.bean.User;
import com.xut.model.Code;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import com.xut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public String home() {
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public NoneDataResult login(@RequestParam("identificationId") String identificationId,
                                @RequestParam("password") String password, HttpServletRequest request) {
        NoneDataResult result = new NoneDataResult();
        Result<User> userResult = userService.getByIdentificationIdAndPwd(identificationId, password);
        if (userResult.isNotValid()) {
            result.setCode(Code.LOGIN_ERROR);
            return result;
        }
        request.getSession().setAttribute("user", userResult.getData());
        return result;
    }

    @PostMapping("/register")
    @ResponseBody
    public NoneDataResult register(@RequestBody User user) {
        NoneDataResult result = new NoneDataResult();
        if (user == null) {
            result.setCode(Code.INVALID_PARAM);
        }
        userService.create(user);
        return result;
    }

}
