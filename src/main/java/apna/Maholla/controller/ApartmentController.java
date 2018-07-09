package apna.Maholla.controller;

import apna.Maholla.model.Apartment;
import apna.Maholla.repository.ApartmentRepository;
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

    @Autowired
    public ApartmentController(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @PostMapping(path = "/addApartment", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> postLoginUser(@RequestBody Apartment apartment) throws Exception {
        apartment.setApartmentUniqueId();
        apartmentRepository.save(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
