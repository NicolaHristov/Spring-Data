package bg.softuni.jsonexercisekmitev.data.repository.carDealerRepo;

import bg.softuni.jsonexercisekmitev.data.entity.carDealerEntity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier,Long> {

//    List<Supplier>findAllByImporterIsEmpty();

    List<Supplier>findAllByIsImporter(boolean isImporter);

}
