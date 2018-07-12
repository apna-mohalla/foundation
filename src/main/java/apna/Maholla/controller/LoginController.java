package apna.Maholla.controller;

import apna.Maholla.RequestModels.Login;
import apna.Maholla.RequestModels.SignIn;
import apna.Maholla.ResponceModel.User;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.mappers.FlatMapper;
import apna.Maholla.mappers.GetUserRequestMapper;
import apna.Maholla.mappers.GetUserResponceMapper;
import apna.Maholla.mappers.UpdatedUserMapper;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Roles;
import apna.Maholla.model.Users;
import apna.Maholla.model.Verification;
import apna.Maholla.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginRepository loginRepository;
    private ApartmentRepository apartmentRepository;
    private RoleRepository roleRepository;
    private VerificationRepository verificationRepository;
    private FlatRepository flatRepository;

    @Autowired
    public LoginController(LoginRepository loginRepository, ApartmentRepository apartmentRepository, RoleRepository roleRepository, VerificationRepository verificationRepository, FlatRepository flatRepository) {
        this.loginRepository = loginRepository;
        this.apartmentRepository = apartmentRepository;
        this.roleRepository = roleRepository;
        this.verificationRepository = verificationRepository;
        this.flatRepository = flatRepository;
    }

    @PostMapping("/getUser")
    public User getUser(@RequestBody Login login) {
        Users user =  loginRepository.findByUserid(login.userid);
        GetUserResponceMapper getUser = new GetUserResponceMapper();
        if(user == null)
            user = loginRepository.findByEmailid(login.userid);
        if(user != null){
            Apartment apartment = apartmentRepository.findFirstById(user.apartmentkey);
            Roles role = roleRepository.findFirstById(user.role);
            Verification verification = verificationRepository.findFirstByUserid(user.emailid);
            getUser.setUserDetails(user, apartment, role, verification);
        }
        return getUser.userDetails;
    }

    @PostMapping("/getAllUsers")
    public List<Users> getAllUsers(@RequestBody Apartment apartment){
        Apartment apartment1 = apartmentRepository.findByApartmentuniqueid(apartment.apartmentuniqueid);
        return loginRepository.findAllByApartmentkey(apartment1.getId());
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
        FlatMapper flatMapper = new FlatMapper();
        flatMapper.setFlat(userRequest, apartment);
        flatRepository.save(flatMapper.flat);
        return new ResourceSavesSuccess("User", "UserId", userRequest.userid, "Sucess", "User signed up successfully");
    }

    @PostMapping(path = "/login", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Users postLoginUser(@RequestBody Login login) throws Exception {
        Users user = loginRepository.findByUseridAndPassword(login.userid, login.getPassword());
        if(user == null){
            user = loginRepository.findByEmailidAndPassword(login.userid, login.getPassword());
        }
        return user;
    }

    @PostMapping(path = "/verifications", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public Verification getAllVerifications(@RequestBody Verification verification) throws Exception {
        return verificationRepository.findFirstByUserid(verification.userid);
    }

    @PostMapping(path = "/updateUserDetails", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound updateUserDetails(@RequestBody Users newUserDetails) throws Exception {
        Users oldUserDetails = loginRepository.findByEmailid(newUserDetails.emailid);
        Users userWithSameUserId = loginRepository.findByUserid(newUserDetails.userid);
        if(userWithSameUserId == null)
            return new ResourceNotFoundException("User", "UserId", newUserDetails.userid, "All ready exit", "User with this userid all ready exist");
        UpdatedUserMapper updatedUserMapper = new UpdatedUserMapper(newUserDetails, oldUserDetails);
        updatedUserMapper.setUpdatedUser();
        loginRepository.delete(oldUserDetails);
        loginRepository.save(updatedUserMapper.updatedUser);
        return new ResourceSavesSuccess("User", "UserId", newUserDetails.userid, "Sucess", "Userid Updated");
    }
}