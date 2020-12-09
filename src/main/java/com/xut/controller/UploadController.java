package com.xut.controller;

import com.xut.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadController {

    @Autowired
    QiniuService qiniuService;

    @GetMapping("/getToken")
    public Map<String, Object> getToken() {
        Map<String, Object> map = new HashMap<>();
        map.put("uptoken", qiniuService.getToken());
        return map;
    }

}
