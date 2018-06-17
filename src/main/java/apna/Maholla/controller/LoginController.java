package apna.Maholla.controller;

import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.model.User;
import apna.Maholla.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    LoginRepository loginRepository;

    @GetMapping("/login/{id}")
    public User getAllNotes(@PathVariable(value = "id") Long userId) {
        return loginRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
}