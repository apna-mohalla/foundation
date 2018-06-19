package apna.Maholla.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "Attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private User user;

    @NotBlank
    private Date enterTime;

    @NotBlank
    private Date exitTime;
}
