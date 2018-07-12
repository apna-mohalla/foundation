package apna.Maholla.mappers;

import apna.Maholla.RequestModels.SignIn;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Flat;

public class FlatMapper {
    public Flat flat;

    public FlatMapper(){
        flat = new Flat();
    }

    public void setFlat(SignIn userRequest, Apartment apartment) {
        flat.apartmentid = apartment.getId();
        flat.block = userRequest.block;
        flat.flatnumber = userRequest.flatnumber;
    }
}
