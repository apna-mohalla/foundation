package apna.Maholla.controller;

import apna.Maholla.RequestModels.VehicleRequestMapper;
import apna.Maholla.ResponceModel.VehicleResponceModel;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.mappers.VehicleResponceMapper;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Flat;
import apna.Maholla.model.Users;
import apna.Maholla.model.Vehicle;
import apna.Maholla.repository.ApartmentRepository;
import apna.Maholla.repository.FlatRepository;
import apna.Maholla.repository.LoginRepository;
import apna.Maholla.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {
    private VehicleRepository vehicleRepository;
    private LoginRepository loginRepository;
    private ApartmentRepository apartmentRepository;
    private FlatRepository flatRepository;

    @Autowired
    public VehicleController(VehicleRepository vehicleRepository, LoginRepository loginRepository, ApartmentRepository apartmentRepository, FlatRepository flatRepository) {
        this.vehicleRepository = vehicleRepository;
        this.loginRepository = loginRepository;
        this.apartmentRepository = apartmentRepository;
        this.flatRepository = flatRepository;
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
        return new ResourceSavesSuccess("Vehicle", "Vehicle Number", vehicle.vehiclename, "sucess", "Vehicle added");
    }

    @PostMapping(path = "/getVehicle", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public VehicleResponceModel getVehicle(@RequestBody VehicleRequestMapper vehicleRequestMapper) {
        VehicleResponceMapper vehicleResponceMapper = new VehicleResponceMapper();
        Vehicle vehicle = vehicleRepository.findByVehiclenumber(vehicleRequestMapper.vehiclenumber);
        Users user = loginRepository.findByEmailid(vehicle.userid);
        if (vehicle.shouldcontactowner) {
            Flat flat = flatRepository.findByApartmentidAndAndFlatnumberAndAndBlock(user.apartmentkey, user.flatnumber, user.block);
            user = loginRepository.findById(flat.ownerid);
        }
        vehicleResponceMapper.setVehicleResponceModels(vehicle, user);
        return vehicleResponceMapper.vehicleResponceModel;
    }

    @PostMapping(path = "/updateVehicle", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound updateVehicle(@RequestBody Vehicle vehicleRequest) {
        Vehicle vehicle = vehicleRepository.findByVehiclenumber(vehicleRequest.vehiclenumber);
        Users user = loginRepository.findByEmailid(vehicle.userid);
        List<Users> users = loginRepository.findAllByApartmentkeyAndBlockAndFlatnumber(user.apartmentkey, user.block, user.flatnumber);
        boolean authoried = false;
        for(Users usr: users) {
            if(usr.emailid.equalsIgnoreCase(vehicleRequest.userid)){
                authoried = true;
            }
        }
        if(!authoried){
            return new ResourceNotFoundException("User", "Vehicle Number", vehicleRequest.userid, "Forbidden", "You are not autorised to update");
        }
        vehicleRepository.delete(vehicle);
        vehicleRepository.save(vehicleRequest);
        return new ResourceSavesSuccess("Vehicle", "Vehicle Number", vehicle.vehiclename, "sucess", "Vehicle Updated");
    }

    @PostMapping(path = "/deleteVehicle", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound deleteVehicle(@RequestBody Vehicle vehicleRequest) {
        Vehicle vehicle = vehicleRepository.findByVehiclenumber(vehicleRequest.vehiclenumber);
        Users user = loginRepository.findByEmailid(vehicle.userid);
        List<Users> users = loginRepository.findAllByApartmentkeyAndBlockAndFlatnumber(user.apartmentkey, user.block, user.flatnumber);
        boolean authoried = false;
        for(Users usr: users) {
            if(usr.emailid.equalsIgnoreCase(vehicleRequest.userid)){
                authoried = true;
            }
        }
        if(!authoried){
            return new ResourceNotFoundException("User", "Vehicle Number", vehicleRequest.userid, "Forbidden", "You are not autorised to update");
        }
        vehicleRepository.delete(vehicle);
        return new ResourceSavesSuccess("Vehicle", "Vehicle Number", vehicle.vehiclename, "sucess", "Vehicle Updated");
    }

    @PostMapping(path = "/getAllUserVehicle", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<VehicleResponceModel> getAllUserVehicle(@RequestBody VehicleRequestMapper vehicleRequestMapper) {
        VehicleResponceMapper vehicleResponceMapper = new VehicleResponceMapper();
            Users user = loginRepository.findByEmailid(vehicleRequestMapper.userid);
            List<Users> users = loginRepository.findAllByApartmentkeyAndBlockAndFlatnumber(user.apartmentkey, user.block, user.flatnumber);
            for(Users usr: users) {
                List<Vehicle> vehicles = vehicleRepository.findByUserid(usr.emailid);
                for (Vehicle vehicle : vehicles) {
                    vehicleResponceMapper.mapAllVehicleResponceModels(vehicle, usr);
                }
            }
        return vehicleResponceMapper.vehicleResponceModels;
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
