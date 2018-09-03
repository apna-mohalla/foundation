package apna.Maholla.controller;

import apna.Maholla.RequestModels.CommonRequestMapper;
import apna.Maholla.RequestModels.Login;
import apna.Maholla.ResponceModel.ResponceFeatureMapper;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.mappers.RoleFeatureMapper;
import apna.Maholla.model.ApartmentRoleFeature;
import apna.Maholla.model.Features;
import apna.Maholla.model.Roles;
import apna.Maholla.model.Users;
import apna.Maholla.repository.ApartmentRoleFeatureRepository;
import apna.Maholla.repository.FeaturesRepository;
import apna.Maholla.repository.LoginRepository;
import apna.Maholla.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FeatureController {

    private FeaturesRepository featuresRepository;
    private LoginRepository loginRepository;
    private ApartmentRoleFeatureRepository apartmentRoleFeatureRepository;
    private RoleRepository roleRepository;

    @Autowired
    public FeatureController(FeaturesRepository featuresRepository, LoginRepository loginRepository, ApartmentRoleFeatureRepository apartmentRoleFeatureRepository, RoleRepository roleRepository) {
        this.featuresRepository = featuresRepository;
        this.loginRepository = loginRepository;
        this.apartmentRoleFeatureRepository = apartmentRoleFeatureRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping(path = "/getAllFeatures", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<ResponceFeatureMapper> getAllAvailableFeature() {
        List<ResponceFeatureMapper> responceFeatureMappers = new ArrayList<>();
        for (Features feature: featuresRepository.findAll()) {
            responceFeatureMappers.add(new ResponceFeatureMapper(feature.featurename, feature.description));
        };
        return responceFeatureMappers;
    }

    @PostMapping(path = "/getFeatures", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<ResponceFeatureMapper> getUsersFeature(@RequestBody Login login) {
        if(login.userid != null) {
            Users user = loginRepository.findByEmailid(login.userid);
            if (user != null) {
                Roles role = roleRepository.findFirstById(user.role);
                List<ResponceFeatureMapper> responceFeatureMappers = new ArrayList<>();
                for (Features feature : featuresRepository.findAll()) {
                    responceFeatureMappers.add(new ResponceFeatureMapper(feature.featurename, feature.description));
                }
                return responceFeatureMappers;
            }
        }
        return null;
    }

    @PostMapping(path = "/setFeatures", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResourceFoundNotFound setFeaturesToRoles(@RequestBody CommonRequestMapper userApartmentRequestMapper) {
        Users user = loginRepository.findByEmailid(userApartmentRequestMapper.userid);
        ResourceNotFoundException resourceNotFoundException = checkForAuthentication(userApartmentRequestMapper, user);
        if (resourceNotFoundException != null)
            return resourceNotFoundException;
        Roles role = roleRepository.findFirstByRoleName(userApartmentRequestMapper.role);
        if (role == null)
            return new ResourceNotFoundException("Role", "Role name", userApartmentRequestMapper.role, "Not Found", "Role not supported");
        List<Features> featureDis = new ArrayList<>();
        for (String feature : userApartmentRequestMapper.features) {
            Features feature1 = featuresRepository.findByFeaturename(feature);
            if (feature1 == null)
                break;
            featureDis.add(feature1);
        }
        if (featureDis.size() != userApartmentRequestMapper.features.size())
            return new ResourceNotFoundException("Features", "Name", "", "Not Found", "Some of the features not supported");
        addFeatureRoleMap(user, featureDis, role.getId());
        return new ResourceSavesSuccess("Features", "", userApartmentRequestMapper.userid, "sucess", "Feature added to the role");
    }

    private ResourceNotFoundException checkForAuthentication(CommonRequestMapper userApartmentRequestMapper, Users user) {
        if (user == null)
            return new ResourceNotFoundException("User", "UserId", userApartmentRequestMapper.userid, "Not Found", "User with this userid does not exist");
        Roles roleOfUser = roleRepository.findFirstById(user.role);
        if (user.role == 0)
            return new ResourceNotFoundException("User", "Role", userApartmentRequestMapper.userid, "Forbidden", "You are not authorized to align roles");
        if (roleOfUser.roleName.equalsIgnoreCase("Admin") || roleOfUser.roleName.equalsIgnoreCase("Super Admin"))
            return new ResourceNotFoundException("User", "Role", userApartmentRequestMapper.userid, "Forbidden", "You are not authorized to align roles");

        return null;
    }

    private void addFeatureRoleMap(Users user, List<Features> featureDis, int roleId) {
        for (Features features : featureDis) {
            RoleFeatureMapper roleFeatureMapper = new RoleFeatureMapper(user.apartmentkey, features.getId(), roleId);
            if (apartmentRoleFeatureRepository.findFirstByApartmentAndFeatureAndRole(roleFeatureMapper.apartment, roleFeatureMapper.feature, roleFeatureMapper.role) == null) {
                ApartmentRoleFeature apartmentRoleFeature = new ApartmentRoleFeature();
                apartmentRoleFeature.apartment = user.apartmentkey;
                apartmentRoleFeature.role = user.role;
                apartmentRoleFeature.feature = features.getId();
                apartmentRoleFeatureRepository.save(apartmentRoleFeature);
            }
        }
    }

}
