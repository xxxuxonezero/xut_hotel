package com.xut.controller;

import com.xut.controller.auth.AuthUtil;
import com.xut.filter.Identity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public Map<String, Object> getUserModel(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Identity identity = AuthUtil.getIdentity(request);
        map.put("user", identity);
        return map;
    }
}
