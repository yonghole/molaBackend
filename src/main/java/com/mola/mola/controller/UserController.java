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

    @GetMapping("test/")
    public String say(){
        return "hello world!";
    }

    @PostMapping("login/")
    public Boolean login(@RequestBody Map<String, Object> m){
        String email = m.get("email").toString();
        String password = m.get("password").toString();

        if(userService.login(email,password)){
            return true;
        }
        else{
            return false;
        }
    }

    @PostMapping("signup/")
    public Boolean create(@RequestBody Map<String, Object> m){
        System.out.println("encountered\n");
       User user = new User();

       user.setEmail(m.get("email").toString());
       user.setPassword(m.get("password").toString());
       user.setName(m.get("name").toString());
       user.setPhonenum(m.get("phonenum").toString());
       user.setPoint(0);

       if(userService.join(user)){
           return true;
       }
       System.out.println("111");
       return false;
    }


    //@RequestMapping(value = "member/", method = RequestMethod.POST, produces = "application/json; charset=utf8");
}
