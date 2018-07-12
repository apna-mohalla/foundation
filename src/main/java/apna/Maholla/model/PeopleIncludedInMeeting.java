package apna.Maholla.model;

import javax.persistence.*;

@Entity
@Table(name = "PeopleIncludedInMeeting")
public class PeopleIncludedInMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    public int userid;

    public int meetingid;

    public boolean willattend;
}
