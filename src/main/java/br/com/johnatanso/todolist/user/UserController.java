package br.com.johnatanso.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity createUser(@RequestBody UserModel userModel) {

        var userAlreadyExists = this.userRepository.findByEmail(userModel.getEmail());

        if (userAlreadyExists != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado no sistema");
        }

        var newUser = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}

