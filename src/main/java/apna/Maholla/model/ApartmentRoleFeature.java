package apna.Maholla.model;

import javax.persistence.*;

@Entity
@Table(name = "ApartmentRoleFeature")
public class ApartmentRoleFeature {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    public int apartment;

    public int role;

    public int feature;
}
