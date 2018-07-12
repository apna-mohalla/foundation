package apna.Maholla.model;

import javax.persistence.*;

@Entity
@Table(name = "Flat")
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int apartmentid;

    public int ownerid;

    public int membernumber;

    public String flatnumber;

    public String block;
}
