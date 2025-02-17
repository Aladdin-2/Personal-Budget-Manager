package com.aladdin.personalbudgetcontrollerdemo2.controller;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.User;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseUserDto;
import com.aladdin.personalbudgetcontrollerdemo2.security.RegisterService;
import com.aladdin.personalbudgetcontrollerdemo2.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "aladdin.com/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    private final RegisterService registerService;


    @PostMapping(path = "/register")
    public void registerUser(@RequestBody User user) throws Exception {
        registerService.registerUser(user);
    }

    @PostMapping(path = "/login")
    public void loginUser(@RequestBody ResponseUserDto userDto) throws Exception {
        userService.loginUser(userDto);
    }

    @GetMapping(path = "/checkPassword")
    public User checkPassword(@RequestParam("password") String encryptPassword) throws Exception {
        return userService.checkingPassword(encryptPassword);
    }
}
