package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.exam.models.entity.Book;

import java.util.Optional;
import java.util.Set;

// TODO:
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book>findByTitle(String title);
//•	Filter only books that are SCIENCE_FICTION and order them by the borrow date in descending order
//    @Query("SELECT b FROM Book  b JOIN BorrowingRecord br ON b.id=br.borrowDate WHERE b.genre = 'SCIENCE_FICTION' ORDER BY br.borrowDate ASC")
//    Set<Book>findAllByGenreOrder(String genre);
}
