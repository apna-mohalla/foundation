package apna.Maholla.controller;

import apna.Maholla.RequestModels.Login;
import apna.Maholla.RequestModels.SignIn;
import apna.Maholla.ResponceModel.User;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.mappers.GetUserRequestMapper;
import apna.Maholla.mappers.GetUserResponceMapper;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Roles;
import apna.Maholla.model.Users;
import apna.Maholla.repository.ApartmentRepository;
import apna.Maholla.repository.LoginRepository;
import apna.Maholla.repository.RoleRepository;
import apna.Maholla.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginRepository loginRepository;
    private ApartmentRepository apartmentRepository;
    private RoleRepository roleRepository;
    private VerificationRepository verificationRepository;

    @Autowired
    public LoginController(LoginRepository loginRepository, ApartmentRepository apartmentRepository, RoleRepository roleRepository, VerificationRepository verificationRepository) {
        this.loginRepository = loginRepository;
        this.apartmentRepository = apartmentRepository;
        this.roleRepository = roleRepository;
        this.verificationRepository = verificationRepository;
    }

    @PostMapping("/getUser")
    public User getAllUsers(@RequestBody Login login) {
        Users user =  loginRepository.findByUserid(login.userid);
        GetUserResponceMapper getUser = new GetUserResponceMapper();
        if(user == null)
            user = loginRepository.findByEmailid(login.userid);
        if(user != null){
            Apartment apartment = apartmentRepository.findFirstById(user.apartmentkey);
            Roles role = roleRepository.findFirstById(user.role);
            getUser.setUserDetails(user, apartment, role);
        }
        return getUser.userDetails;
    }

    @PostMapping(path = "/signUp", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound signUp(@RequestBody SignIn userRequest) throws Exception {
        GetUserRequestMapper getUserRequestMapper = new GetUserRequestMapper();
        Apartment apartment = apartmentRepository.findByApartmentuniqueid(userRequest.apartmentkey);
        if(apartment == null){
            return new ResourceNotFoundException("Apartment", "Apartment key", userRequest.apartmentkey, "Not Found", "Apartment With given key not found");
        }
        getUserRequestMapper.setUser(userRequest, apartment);
        loginRepository.save(getUserRequestMapper.user);
        verificationRepository.save(getUserRequestMapper.verification);
        return new ResourceSavesSuccess("User", "UserId", userRequest.userid, "Sucess", "User signed in successfully");
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