package com.xut.controller;

import com.xut.bean.User;
import com.xut.controller.auth.AuthUtil;
import com.xut.filter.Identity;
import com.xut.model.Code;
import com.xut.model.Constant;
import com.xut.model.NoneDataResult;
import com.xut.model.Result;
import com.xut.service.UserService;
import com.xut.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class HomeController extends BaseController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public ModelAndView home(HttpServletRequest request) {
        return new ModelAndView("index", getUserModel(request));
    }

    @PostMapping("/login")
    public NoneDataResult login(@RequestParam("identificationId") String identificationId,
                                @RequestParam("password") String password,
                                HttpServletResponse response) {
        NoneDataResult result = new NoneDataResult();
        Result<User> userResult = userService.getByIdentificationIdAndPwd(identificationId, password);
        if (userResult.isNotValid()) {
            result.setCode(Code.LOGIN_ERROR);
            return result;
        }
        Identity identity = new Identity();
        User user = userResult.getData();
        identity.setUserId(user.getId());
        identity.setUserName(user.getUserName());
        identity.setType(user.getType());
        String s = JWTUtils.generateJwt(identity);
        response.addCookie(new Cookie(Constant.IDENTITY, s));
        return result;
    }

    @PostMapping("/register")
    public NoneDataResult register(@RequestBody User user) {
        NoneDataResult result = new NoneDataResult();
        if (user == null) {
            result.setCode(Code.INVALID_PARAM);
        }
        result = userService.create(user);
        return result;
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthUtil.clearIdentity(response);
        response.sendRedirect(request.getContextPath());
        return;
    }
}
