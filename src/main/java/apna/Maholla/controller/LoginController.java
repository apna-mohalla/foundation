package apna.Maholla.controller;

import apna.Maholla.exception.ResourceNotFoundException;
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
    public User getAllNotes(@PathVariable(value = "id") String userId) {
        return loginRepository.findById(Integer.parseInt(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PostMapping("/login")
    public  User postLoginUser(){
        return new User();
    }
}