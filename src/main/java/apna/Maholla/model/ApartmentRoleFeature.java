package apna.Maholla.model;

import javax.persistence.*;

@Entity
@Table(name = "ApartmentRoleFeature")
public class ApartmentRoleFeature {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private int apartment;

    private int role;

    private int feature;
}
