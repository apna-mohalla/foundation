package apna.Maholla.repository;

import apna.Maholla.model.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Integer> {

    Flat findByApartmentidAndAndFlatnumberAndAndBlock(int apartmentkey, String flatnumber, String block);
}
