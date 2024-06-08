package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Sale;
import softuni.exam.models.entity.Seller;

import java.util.Optional;
import java.util.Set;

//TODO
public interface SellerRepository extends JpaRepository<Seller,Long> {

//    Optional<Set<Seller>>findAllBySellerLastName(String seller_lastName);

    Optional<Seller>findSellerByLastName(String lastName);

    Optional<Seller> findSellerByPersonalNumber (String personalNumber);

    Seller findSellerById(long id);
}
