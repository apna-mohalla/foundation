package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    public String userid;

    public String vehiclename;

    public String vehicletype;

    public String image;

    @org.hibernate.annotations.ColumnDefault("false")
    public boolean shouldcontactowner;

    @Column(nullable = false, unique = true)
    public String vehiclenumber;

    @Column(nullable = false)
    private int apartmentkey;

    public void setApartmentkey(int apartmentkey) {
        this.apartmentkey = apartmentkey;
    }
}
