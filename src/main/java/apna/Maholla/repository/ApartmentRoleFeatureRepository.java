package apna.Maholla.repository;

import apna.Maholla.model.ApartmentRoleFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRoleFeatureRepository extends JpaRepository<ApartmentRoleFeature, Integer>{

    ApartmentRoleFeature findFirstByApartmentAndFeatureAndRole(int apartment,int feature,int role);

}
