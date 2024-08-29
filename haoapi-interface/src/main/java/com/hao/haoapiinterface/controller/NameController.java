package com.hao.haoapiinterface.controller;

import com.hao.haoapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称 API
 *
 *
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("get")
    public String getNameByGet(String name, HttpServletRequest request) {
        System.out.println(request.getHeader("hao"));
        return "GET 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "POST 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        // 鉴权逻辑放在网关而不是放在被调用的接口
        String result = "POST 用户名字是" + user.getUsername();
        return result;
    }
}
