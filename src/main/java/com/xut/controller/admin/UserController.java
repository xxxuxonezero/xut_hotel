package com.xut.controller.admin;

import com.xut.bean.User;
import com.xut.model.Result;
import com.xut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/list")
    public Result<List<User>> getUsers() {
        Result<List<User>> result = new Result<>();

        return result;
    }
}
