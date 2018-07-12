package apna.Maholla.repository;

import apna.Maholla.model.MeetingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<MeetingSchedule, Integer> {

}
