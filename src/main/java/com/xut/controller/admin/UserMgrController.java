package com.xut.controller.admin;

import com.xut.bean.Order;
import com.xut.bean.User;
import com.xut.controller.BaseController;
import com.xut.model.NoneDataResult;
import com.xut.model.Page;
import com.xut.model.Result;
import com.xut.service.OrderService;
import com.xut.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/user")
public class UserMgrController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @GetMapping("/list")
    public Result<Page<User>> getUsers(@RequestParam(value = "type", required = false) Integer type,
                                       @RequestParam(value = "old", required = false) Integer old,
                                       @RequestParam(value = "offset", required = false, defaultValue = "1") Integer offset,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        Result<Page<User>> result = new Result<>();
        Result<Page<User>> usersResult = userService.search(type, old, offset, pageSize);
        if (usersResult.isNotValid()) {
            result.setCode(usersResult.getCode());
            return result;
        }
        result.setData(usersResult.getData());
        return result;
    }

    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpServletRequest request) {
        return new ModelAndView("/admin/userInfo", getUserModel(request));
    }

    @GetMapping("/editUser")
    public Result<User> getUser(@RequestParam("id") Integer id) {
        Result<User> result = new Result<>();
        Result<List<User>> userResult = userService.getByIds(Collections.singletonList(id));
        if (userResult.isNotValid()) {
            result.setCode(userResult.getCode());
            return result;
        }
        result.setData(userResult.getData().get(0));
        return result;
    }

    @PostMapping("/editUser")
    public NoneDataResult editUser(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/addUser")
    public NoneDataResult add(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/delete")
    public NoneDataResult delete(@RequestParam("ids") List<Integer> ids) {
        return userService.delete(ids);
    }
}
