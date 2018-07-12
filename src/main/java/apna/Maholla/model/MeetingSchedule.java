package apna.Maholla.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MeetingSchedule")
public class MeetingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    public int creater;
    public String agenda;
    public String venue;
    public String discription;
    public Date timeofthemeeting;
    public int durationinminutes;
    public String minutesofmeeting;

}
