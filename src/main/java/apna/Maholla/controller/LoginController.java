package apna.Maholla.controller;

import apna.Maholla.RequestModels.Login;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.model.User;
import apna.Maholla.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginRepository loginRepository;

    @Autowired
    public LoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @GetMapping("/login/{id}")
    public User getAllNotes(@PathVariable(value = "id") String UserID) {
        return loginRepository.findById(Integer.parseInt(UserID))
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", UserID));
    }

    @PostMapping(path = "/signUp", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> signUp(@RequestBody User user) throws Exception {
        user.setPassword();
        loginRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/login", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public  User postLoginUser(@RequestBody Login login) throws Exception {
        return loginRepository.findByUserID(login.getUserID()).get(1);
    }
}