package apna.Maholla.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "Notice")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;


    private int apartmentkey;

    @NotBlank
    public String subject;

    @NotBlank
    @Length(max = 1000)
    public String description;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    public Date date;

    @NotBlank
    public String userid;

    @NotBlank
    public String designation;

    public void setApartmentkey(int apartmentkey) {
        this.apartmentkey = apartmentkey;
    }
}
