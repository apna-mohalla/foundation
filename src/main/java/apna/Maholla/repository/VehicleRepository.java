package apna.Maholla.repository;

import apna.Maholla.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Vehicle findByVehiclenumber(String vehiclenumber);

    List<Vehicle> findByUserid(String userId);

    List<Vehicle> findAllByApartmentkey(int apartmentKey);
}
