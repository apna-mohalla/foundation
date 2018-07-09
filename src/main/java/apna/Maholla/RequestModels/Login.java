package apna.Maholla.RequestModels;

import apna.Maholla.Miscellaneous.EncryptAndDecrypt;

public class Login {
    private String userid;
    private String password;

    public String getUserID() {
        return this.userid;
    }

    public String getPassword() throws Exception {
        return EncryptAndDecrypt.encrypt(this.password);
    }
}
