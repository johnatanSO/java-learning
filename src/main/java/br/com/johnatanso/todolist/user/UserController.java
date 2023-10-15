package br.com.johnatanso.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
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

        var hasPassword = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(hasPassword);

        var newUser = this.userRepository.save(userModel);


        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}

