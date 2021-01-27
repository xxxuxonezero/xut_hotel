package com.xut.controller.user;

import com.xut.bean.User;
import com.xut.controller.BaseController;
import com.xut.controller.auth.AuthUtil;
import com.xut.filter.Identity;
import com.xut.model.Code;
import com.xut.model.Result;
import com.xut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @GetMapping("/MyInfo")
    public ModelAndView myInfo(HttpServletRequest request) {
        return new ModelAndView("user/userInfo", getUserModel(request));
    }
}
