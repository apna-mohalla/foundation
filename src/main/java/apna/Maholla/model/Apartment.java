package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Apartment")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;          //PK int

    @NotBlank
    private String ApartmentName;           //UNIQUE

    @NotBlank
    private String ApartmentUniqueId;          // string UNIQUE

    @NotBlank
    private String Address1;         // string

    @NotBlank
    private String Address2;             // string

    @NotBlank
    @org.hibernate.annotations.ColumnDefault("false")
    private Boolean HasBlocks;

    @ManyToOne
    private State state;

    @ManyToOne
    private City city;

    @ManyToOne
    private Country country;

    @NotBlank
    private long pinCode;

}
