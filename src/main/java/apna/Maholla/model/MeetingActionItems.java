package apna.Maholla.model;

import javax.persistence.*;

@Entity
@Table
public class MeetingActionItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private int meetingid;

    public String actionitems;

    public String assignedto;

}
