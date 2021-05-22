package com.mola.mola.controller;

import com.mola.mola.domain.User;
import com.mola.mola.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("member/")
    public Boolean create(@RequestBody Map<String, Object> m){
       User user = new User();

       user.setEmail(m.get("email").toString());
       user.setPassword(m.get("password").toString());
       user.setName(m.get("name").toString());
       user.setPhonenum(m.get("phonenum").toString());
       user.setPoint(0);

       userService.join(user);
       return true;
    }


    //@RequestMapping(value = "member/", method = RequestMethod.POST, produces = "application/json; charset=utf8");
}
