package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.LibraryMember;

import java.util.Optional;

// TODO:
public interface LibraryMemberRepository extends JpaRepository<LibraryMember,Long> {

    Optional<LibraryMember>findByPhoneNumber(String phoneNumber);

//    Optional<LibraryMember>findById(long id);


}
