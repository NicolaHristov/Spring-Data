package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book>findAllByAgeRestriction(AgeRestriction ageRestriction);

    //2.Write a program that prints the titles of the golden edition books, which have less than 5000 copies.
    List<Book>findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);
    //3. Books by Price
    //Write a program that prints the titles and prices of books with price lower than 5 and higher than 40.
    List<Book>findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);
    //Примерен вариант на 3 задача с JPQL QUerry
//    @Query("SELECT b FROM Book AS b WHERE b.price between :lower AND :upper")
//    List<Book>findAllByPriceLessThanOrPriceGreaterThanSusJPQLQuery(@Param(value ="upper")BigDecimal lower, @Param(value = "upper") BigDecimal upper);

    //4.	Not Released Books
    //Write a program that prints the titles of all books that are NOT released in a given year.
    List<Book>findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate lower, LocalDate upper);

    //5.	Books Released Before Date
    //Write a program that prints the title, the edition type and the price of books, which are released before a given date. The date will be in the format dd-MM-yyyy.
    List<Book>findBookByReleaseDateBefore(LocalDate releaseDate);

    //7.	Books Search
    //Write a program that prints the titles of books, which contain a given string (regardless of the casing).
    List<Book> findAllByTitleContaining(String title);

    //8.	Book Titles Search
    //Write a program that prints the titles of books, which are written by authors, whose last name starts with a given string.
    List<Book>findAllByAuthor_LastNameStartsWith(String inputString);


    //9.	Count Books
    //Write a program that prints the number of books, whose title is longer than a given number
    @Query("SELECT count(b) From Book  AS b WHERE length(b.title) > :param")
    int countOfBookWithTitleLengthMoreThan(@Param(value = "param") int titleLength);


    @Procedure("change_book_price_by_id")
    int changeBookByPriceId(Long book_id);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.copies = b.copies + :copies WHERE b.releaseDate > :date")
    int updateCopiesByReleaseDate(@Param(value = "copies")int copies,@Param(value = "date") LocalDate date);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.copies = b.copies + :amount WHERE b.releaseDate > :date")
    int addCopiesToBookAfter(LocalDate date, int amount);
    @Query("SELECT b.title AS title, b.editionType AS editionType, b.ageRestriction AS ageRestriction, b.price AS price FROM Book b WHERE b.title = :titleStr")
    BookSummary findSummaryForTitle(String titleStr);

    @Query("SELECT b.title  AS title, b.price AS price FROM Book AS b WHERE b.title = :titleBook")
//    @Query("SELECT b.title, b.editionType, b.ageRestriction, b.price FROM Book AS b WHERE b.title = :titleBook")
    List<Book>findReducedBook(String titleBook);
    @Transactional
    int deleteByCopiesLessThan(int amount);

}
