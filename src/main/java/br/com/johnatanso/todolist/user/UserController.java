package br.com.johnatanso.todolist.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public String getUserInfo() {
        return "User info";
    }

    @PostMapping("/")
    public UserModel create(@RequestBody UserModel userModel) {
        System.out.println(userModel.name);
        return userModel;
    }
}
