package apna.Maholla.controller;

import apna.Maholla.RequestModels.VehicleRequestMapper;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Users;
import apna.Maholla.model.Vehicle;
import apna.Maholla.repository.ApartmentRepository;
import apna.Maholla.repository.LoginRepository;
import apna.Maholla.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {
    private VehicleRepository vehicleRepository;
    private LoginRepository loginRepository;
    private ApartmentRepository apartmentRepository;

    @Autowired
    public VehicleController(VehicleRepository vehicleRepository, LoginRepository loginRepository, ApartmentRepository apartmentRepository) {
        this.vehicleRepository = vehicleRepository;
        this.loginRepository = loginRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @PostMapping(path = "/addVehicle", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound addApartment(@RequestBody Vehicle vehicle) throws Exception {
        Users user = loginRepository.findByEmailid(vehicle.userid);
        if(user == null){
            return new ResourceNotFoundException("User", "UserId", vehicle.userid, "Do not exit", "User with this userid is not registered");
        }
        if(vehicleRepository.findByVehiclenumber(vehicle.vehiclenumber) != null){
            return new ResourceNotFoundException("Vehicle", "Vehicle Number", vehicle.vehiclenumber, "Allready exit", "Vehicle with this vehicle number allready exist");
        }
        vehicle.setApartmentkey(user.apartmentkey);
        vehicleRepository.save(vehicle);
        return new ResourceSavesSuccess("Vehicle", "Vehicle Number", vehicle.vehiclename, "Sucess", "Vehicle added");
    }

    @PostMapping(path = "/getVehicle", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Vehicle> getVehicle(@RequestBody VehicleRequestMapper vehicleRequestMapper){
        if(vehicleRequestMapper.vehiclenumber == null) {
            return vehicleRepository.findByUserid(vehicleRequestMapper.userid);
        }
        return (List<Vehicle>) vehicleRepository.findByVehiclenumber(vehicleRequestMapper.vehiclenumber);
    }

    @PostMapping(path = "/getAllVehicle", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<Vehicle> getAllVehicle(@RequestBody VehicleRequestMapper vehicleRequestMapper){
        if(vehicleRequestMapper.userid != null) {
            Users user = loginRepository.findByEmailid(vehicleRequestMapper.userid);
            return vehicleRepository.findAllByApartmentkey(user.apartmentkey);
        }
        Apartment apartment = apartmentRepository.findByApartmentuniqueid(vehicleRequestMapper.apartmentKey);
        return vehicleRepository.findAllByApartmentkey(apartment.getId());
    }

}
