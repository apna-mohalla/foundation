package apna.Maholla.repository;

import apna.Maholla.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends JpaRepository<Users, Integer> {

    Users findByUseridAndPassword(String userId, String password);

    Users findByEmailidAndPassword(String userId, String password);

    Users findByEmailid(String EmailId);

    Users findByUserid(String UserId);

    Users findById(int id);

    List<Users> findAllByApartmentkey(int apartmentkey);

    List<Users> findAllByApartmentkeyAndBlockAndFlatnumber(int apartmentkey, String flatnumber, String block);
}

