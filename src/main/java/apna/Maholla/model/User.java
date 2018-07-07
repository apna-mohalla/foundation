package apna.Maholla.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;          //PK int

    @NotBlank
    private String UserID;           //UNIQUE

    @NotBlank
    private String EmailID;          // string UNIQUE

    @NotBlank
    private long PhoneNumber;        //UNIQUE

    @NotBlank
    private String image;

    @NotBlank
    private String Password;         // string

    @NotBlank
    private String Name;             // string

    @NotBlank
    private String Block;

    @NotBlank
    private String FlatNumber;       //string

    @ManyToOne
    private Roles Role;               //int FK >- Roles.RoleId

    @ManyToOne
    private Apartment Apartment;         //int FK >- Apartment.AppartmentId


    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

}

