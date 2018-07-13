package apna.Maholla.repository;

import apna.Maholla.model.MeetingActionItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingActionItemsRepository extends JpaRepository<MeetingActionItems, Integer> { }