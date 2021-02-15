package com.xut.controller.admin;

import com.xut.bean.User;
import com.xut.controller.BaseController;
import com.xut.model.Page;
import com.xut.model.Result;
import com.xut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserMgrController extends BaseController {
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public Result<Page<User>> getUsers() {
        Result<Page<User>> result = new Result<>();
        Result<List<User>> usersResult = userService.getAll();
        if (usersResult.isNotValid()) {
            result.setCode(usersResult.getCode());
            return result;
        }
        List<User> users = usersResult.getData();
        Page<User> page = new Page<>(users.size(), users);
        result.setData(page);
        return result;
    }

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpServletRequest request) {
        return new ModelAndView("/admin/userInfo", getUserModel(request));
    }


}
