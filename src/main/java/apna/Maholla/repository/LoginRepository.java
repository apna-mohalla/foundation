package apna.Maholla.repository;

import apna.Maholla.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends JpaRepository<User, Integer> {
    List<User> findByUserID(String UserID);
}
