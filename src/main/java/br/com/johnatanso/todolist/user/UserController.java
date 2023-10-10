package br.com.johnatanso.todolist.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {

    @PostMapping("/")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {

        UserRepository userRepository = new UserRepository();

        UserModel newUser = userRepository.createUser(userModel);

        System.out.print(newUser);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}

