package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "features")
public class Features {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false, unique = true)
    public String featurename;

    @NotBlank
    public String description;

}
