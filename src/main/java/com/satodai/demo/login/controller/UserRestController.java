package com.satodai.demo.login.controller;

import com.satodai.demo.login.domain.model.User;
import com.satodai.demo.login.domain.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    RestService restService;

    /**
     * ユーザー全件取得
     *
     * @return
     */
    @GetMapping("rest/get")
    public List<User> getUserMany() {
        return restService.selectMany();
    }

    @GetMapping("rest/get/{id:.+}")
    public User getUserOne(@PathVariable("id") String id) {
        return restService.selectOne(id);
    }

    @PostMapping("rest/insert")
    public String postUserOne(@RequestBody User user) {

        boolean result = restService.insert(user);

        String str = "";

        if (result) {
            str = "{\"result\":\"ok\"}";
        } else {
            str = "{\"result\":\"error\"}";
        }

        return str;
    }

    @PutMapping("/rest/update")
    public String putUserOne(@RequestBody User user) {

        boolean result = restService.update(user);

        String str = "";

        if (result) {
            str = "{\"result\":\"ok\"}";
        } else {
            str = "{\"result\":\"error\"}";
        }

        return str;
    }

    @DeleteMapping("/rest/delete/{id:.+}")
    public String deleteUserOne(@PathVariable("id") String userId) {

        boolean result = restService.delete(userId);

        if (result) {
            return "{\"result\":\"ok\"}";
        } else {
            return "{\"result\":\"error\"}";
        }
    }
}
