package apna.Maholla.mappers;

import apna.Maholla.ResponceModel.VehicleResponceModel;
import apna.Maholla.model.Users;
import apna.Maholla.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleResponceMapper {
    public List<VehicleResponceModel> vehicleResponceModels;
    public VehicleResponceModel vehicleResponceModel;

    public VehicleResponceMapper(){
        vehicleResponceModels = new ArrayList<>();
        vehicleResponceModel = new VehicleResponceModel();
    }


    public void setVehicleResponceModels(List<VehicleResponceModel> vehicleResponceModels) {
        this.vehicleResponceModels = vehicleResponceModels;
    }

    public void setVehicleResponceModels(Vehicle vehicle, Users user) {
        VehicleResponceModel vehicleResponceModel = new VehicleResponceModel();
        vehicleResponceModel.vehiclename = vehicle.vehiclename;
        vehicleResponceModel.vehiclenumber = vehicle.vehiclenumber;
        vehicleResponceModel.vehicletype = vehicle.vehicletype;
        vehicleResponceModel.emailid = user.emailid;
        vehicleResponceModel.block = user.block;
        vehicleResponceModel.flatnumber = user.flatnumber;
        vehicleResponceModel.image = vehicle.image;
        vehicleResponceModel.name = user.name;
        vehicleResponceModel.phonenumber = user.phonenumber;
        this.vehicleResponceModel = vehicleResponceModel;
    }

    public void mapAllVehicleResponceModels(Vehicle vehicle, Users user) {
        VehicleResponceModel vehicleResponceModel = new VehicleResponceModel();
        vehicleResponceModel.vehiclename = vehicle.vehiclename;
        vehicleResponceModel.vehiclenumber = vehicle.vehiclenumber;
        vehicleResponceModel.vehicletype = vehicle.vehicletype;
        vehicleResponceModel.emailid = user.emailid;
        vehicleResponceModel.block = user.block;
        vehicleResponceModel.flatnumber = user.flatnumber;
        vehicleResponceModel.image = vehicle.image;
        vehicleResponceModel.name = user.name;
        vehicleResponceModel.phonenumber = user.phonenumber;
        vehicleResponceModel.shouldContactOwner = vehicle.shouldcontactowner;
        this.vehicleResponceModels.add(vehicleResponceModel);
    }
}
