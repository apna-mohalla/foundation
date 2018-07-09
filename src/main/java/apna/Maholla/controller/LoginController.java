package apna.Maholla.controller;

import apna.Maholla.RequestModels.Login;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.model.Users;
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
    public Users getAllNotes(@PathVariable(value = "id") String UserID) {
        return loginRepository.findById(Integer.parseInt(UserID))
                .orElseThrow(() -> new ResourceNotFoundException("Users", "id", UserID));
    }

    @PostMapping(path = "/signUp", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> signUp(@RequestBody Users user) throws Exception {
        user.setPassword();
        loginRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/login", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Users postLoginUser(@RequestBody Login login) throws Exception {
        Users user = loginRepository.findByUseridAndPassword(login.userid, login.getPassword());
        if(user == null){
            user = loginRepository.findByEmailidAndPassword(login.userid, login.getPassword());
        }
        return user;
    }
}