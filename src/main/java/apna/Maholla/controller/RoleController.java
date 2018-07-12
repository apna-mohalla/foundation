package apna.Maholla.controller;

import apna.Maholla.RequestModels.ChangeRoleRequest;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.model.Flat;
import apna.Maholla.model.Roles;
import apna.Maholla.model.Users;
import apna.Maholla.repository.FlatRepository;
import apna.Maholla.repository.LoginRepository;
import apna.Maholla.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {
    private final LoginRepository loginRepository;
    private RoleRepository roleRepository;
    private FlatRepository flatRepository;

    @Autowired
    public RoleController(LoginRepository loginRepository, RoleRepository roleRepository, FlatRepository flatRepository) {
        this.loginRepository = loginRepository;
        this.roleRepository = roleRepository;
        this.flatRepository = flatRepository;
    }

    @PostMapping(path = "/setRoles", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResourceFoundNotFound setRolesToUser(@RequestBody ChangeRoleRequest changeRoleRequest) throws Exception {
        Users user = loginRepository.findByUserid(changeRoleRequest.userid);
        if (user == null)
            return new ResourceNotFoundException("User", "UserId", changeRoleRequest.userid, "Not Found", "Userid not found");
        Roles role = roleRepository.findFirstByRoleName(changeRoleRequest.role);
        if (role == null)
            return new ResourceNotFoundException("Roles", "Role", changeRoleRequest.role, "Not Found", "Role not found");
        Flat flat = flatRepository.findByApartmentidAndAndFlatnumberAndAndBlock(user.apartmentkey, user.flatnumber, user.block);
        if (flat.ownerid == user.getId())
            flat.ownerid = 0;
        if (role.roleName.equalsIgnoreCase("owner"))
            if (flat.ownerid != 0)
                return new ResourceNotFoundException("User", "UserId", changeRoleRequest.userid, "Owner", "Flat Already has a owner");
            else flat.ownerid = user.getId();
        if (user.role == 0)
            flat.membernumber += 1;
        user.role = role.getId();
        flatRepository.save(flat);
        loginRepository.save(user);
        return new ResourceSavesSuccess("User", "UserId", changeRoleRequest.userid, "sucess", "Roles Updated Successfully");
    }

    @GetMapping(path = "/getRoles", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Roles> getAllAvailableRoles() throws Exception {
        return roleRepository.findAll();
    }
}
