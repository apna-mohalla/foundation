package apna.Maholla.repository;

import apna.Maholla.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users as u WHERE (userid = userId or emailid = userId) and password = password")
    Users findByUseridAndPassword(@Param("userId") String userId, @Param("password") String password);
}

