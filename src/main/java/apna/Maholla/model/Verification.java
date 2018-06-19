package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "Verification")
public class Verification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User User;

    @NotBlank
    @org.hibernate.annotations.ColumnDefault("false")
    private boolean Apartment;

    @NotBlank
    @org.hibernate.annotations.ColumnDefault("false")
    private boolean EmailID;

    @NotBlank
    @org.hibernate.annotations.ColumnDefault("false")
    private boolean PhoneNumber;
}
