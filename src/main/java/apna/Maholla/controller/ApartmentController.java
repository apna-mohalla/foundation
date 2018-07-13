package apna.Maholla.controller;

import apna.Maholla.RequestModels.UpdateApartmentRequest;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.exception.UnAuthorizedAccess;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.ApartmentRoleFeature;
import apna.Maholla.model.Features;
import apna.Maholla.model.Users;
import apna.Maholla.repository.ApartmentRepository;
import apna.Maholla.repository.ApartmentRoleFeatureRepository;
import apna.Maholla.repository.FeaturesRepository;
import apna.Maholla.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApartmentController {

    private ApartmentRepository apartmentRepository;
    private LoginRepository loginRepository;
    private FeaturesRepository featuresRepository;
    private ApartmentRoleFeatureRepository apartmentRoleFeatureRepository;

    @Autowired
    public ApartmentController(ApartmentRepository apartmentRepository, LoginRepository loginRepository, FeaturesRepository featuresRepository, ApartmentRoleFeatureRepository apartmentRoleFeatureRepository) {
        this.apartmentRepository = apartmentRepository;
        this.loginRepository = loginRepository;
        this.featuresRepository = featuresRepository;
        this.apartmentRoleFeatureRepository = apartmentRoleFeatureRepository;
    }

    @PostMapping(path = "/addApartment", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> addApartment(@RequestBody Apartment apartment) throws Exception {
        apartment.setApartmentUniqueId();
        apartmentRepository.save(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/updateApartmentKey", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound updateApartmentKey(@RequestBody UpdateApartmentRequest updateApartmentRequest) throws Exception {
        if (authorizationCheck(updateApartmentRequest) != null)
            return new UnAuthorizedAccess(updateApartmentRequest.userid);
        Apartment apartment = apartmentRepository.findByApartmentuniqueid(updateApartmentRequest.apartmentuniqueid);
        if(apartment == null)
            return new ResourceNotFoundException("Apartment", "Apartment key", updateApartmentRequest.apartmentuniqueid, "Not Found", "Apartment With given key not found");
        apartment.setApartmentUniqueId();
        String apartmentId = apartment.apartmentuniqueid;
        apartmentRepository.save(apartment);
        return new ResourceSavesSuccess("Apartment", "Apartment key", apartmentId, "sucess", "Apartment key changed successfully");

    }

    private String authorizationCheck(UpdateApartmentRequest updateApartmentRequest) {
        Users user = loginRepository.findByEmailid(updateApartmentRequest.userid);
        if (user == null)
            return "UnAuthorized";
        Features feature = featuresRepository.findByFeaturename("Update Apartment Key");
        System.out.println(feature);
        if (feature == null)
            return "UnAuthorized";
        ApartmentRoleFeature apartmentRoleFeature = apartmentRoleFeatureRepository.findFirstByApartmentAndFeatureAndRole(user.apartmentkey, feature.getId(), user.role);
        if (apartmentRoleFeature == null)
            return "UnAuthorized";
        return null;
    }
}
