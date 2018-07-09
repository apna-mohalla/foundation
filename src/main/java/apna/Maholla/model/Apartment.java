package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Random;

@Entity
@Table(name = "Apartment")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;          //PK int

    @NotBlank
    @Column(nullable = false, unique = true)
    public String ApartmentName;           //UNIQUE

    @Column(nullable = false, unique = true)
    public String ApartmentUniqueId;          // string UNIQUE

    @NotBlank
    public String Address1;         // string

    @NotBlank
    public String Address2;             // string

    @org.hibernate.annotations.ColumnDefault("false")
    public Boolean HasBlocks;

    public int state;

    public int city;

    public int country;

    @Column(nullable = false)
    public int pincode;

    public void setApartmentUniqueId(){
        String removeSpaceFromApartmentName = ApartmentName.replaceAll(" ", "");
        String apartmentSubName = removeSpaceFromApartmentName.length() > 3 ? removeSpaceFromApartmentName.substring(0, 3) : removeSpaceFromApartmentName;
        String changePinCodeToString = "" + pincode;
        String apartmentSubPin = changePinCodeToString.length() > 4 ? changePinCodeToString.substring(0, 2) + changePinCodeToString.substring(changePinCodeToString.length() - 2) : changePinCodeToString;
        Random rand = new Random();
        ApartmentUniqueId = apartmentSubName + apartmentSubPin + (rand.nextInt(8990) + 1000);
    }
}
