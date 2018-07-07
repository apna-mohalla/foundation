package apna.Maholla.controller;

import apna.Maholla.model.User;
import apna.Maholla.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    LoginRepository loginRepository;

    @GetMapping("/login/{id}")
    public String getAllNotes(@PathVariable(value = "id") String userId) {
        return userId;
    }

    @PostMapping("/login")
    public  User postLoginUser(){
        return new User();
    }
}