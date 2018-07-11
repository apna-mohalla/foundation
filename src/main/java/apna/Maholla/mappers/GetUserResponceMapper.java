package apna.Maholla.mappers;

import apna.Maholla.ResponceModel.User;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Roles;
import apna.Maholla.model.Users;

public class GetUserResponceMapper {
    public User userDetails;

    public GetUserResponceMapper(){
        userDetails = new User();
    }

    public void setUserDetails(Users user, Apartment apartment, Roles role){
        userDetails.setName(user.name);
        userDetails.setEmailid(user.emailid);
        mapApartment(apartment);
        userDetails.setBlock(user.block);
        userDetails.setFlatnumber(user.flatnumber);
        userDetails.setImage(user.image);
        userDetails.setPhonenumber(user.phonenumber);
        userDetails.setUserid(user.userid);
        mapRoles(role);
    }

    private void mapRoles(Roles role) {
        if(role == null)
            userDetails.setRole(null);
        else
            userDetails.setRole(role.roleName);
    }

    private void mapApartment(Apartment apartment) {
        if(apartment == null)
            userDetails.setApartmentName(null);
        else
            userDetails.setApartmentName(apartment.apartmentname);
    }
}
