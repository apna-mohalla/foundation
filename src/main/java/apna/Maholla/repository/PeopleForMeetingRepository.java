package apna.Maholla.repository;

import apna.Maholla.model.PeopleIncludedInMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleForMeetingRepository extends JpaRepository<PeopleIncludedInMeeting, Integer> {
}
