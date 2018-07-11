package apna.Maholla.mappers;

import apna.Maholla.RequestModels.SignIn;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Users;

public class GetUserRequestMapper {

    public Users user;

    public GetUserRequestMapper() {
        this.user = new Users();
    }


    public void setUser(SignIn signIn, Apartment apartment) throws Exception {

        this.user.apartmentkey = apartment.getId();
        this.user.block = signIn.block;
        this.user.emailid = signIn.emailid;
        this.user.flatnumber = signIn.flatnumber;
        this.user.image = signIn.image;
        this.user.name = signIn.name;
        this.user.phonenumber = signIn.phonenumber;
        this.user.password = signIn.password;
        this.user.userid = signIn.userid;
        this.user.role = 0;

        user.setPassword();
    }
}
