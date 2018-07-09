package apna.Maholla.RequestModels;

import apna.Maholla.Miscellaneous.EncryptAndDecrypt;

public class Login {
    private String UserID;
    private String password;

    public String getUserID() {
        return this.UserID;
    }

    public String getPassword() throws Exception {
        return EncryptAndDecrypt.encrypt(this.password);
    }
}
