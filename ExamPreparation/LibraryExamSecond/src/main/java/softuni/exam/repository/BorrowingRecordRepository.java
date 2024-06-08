package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {
//    Optional<LibraryMember>findById(long id);

    //Export the Borrowing records before 2021-09-10 from the Database
    //Filter only books that are SCIENCE_FICTION and order them by the borrow date in descending order.
    Set<BorrowingRecord> findAllByBorrowDateBeforeAndBookGenreOrderByBorrowDateDesc(LocalDate borrowDate, String book_genre);


}