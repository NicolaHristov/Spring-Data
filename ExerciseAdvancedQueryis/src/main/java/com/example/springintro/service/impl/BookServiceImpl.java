package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBookAgeRestriction(AgeRestriction ageRestriction) {
        return bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream().map(Book::getTitle).toList();
    }

    @Override
    public List<String> findTheTitlesOfGoldenBooksLessThan5000() {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD,5000)
                .stream().map(Book::getTitle).toList();
    }

    @Override
    public List<String> findBooksByPriceLowerThanHigherThan() {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(new BigDecimal(5),new BigDecimal(40))
                .stream().map( book -> String.format("%s %.2f",book.getTitle(),book.getPrice())).toList();
    }

    @Override
    public List<String> findAllBookTitleNotReleaseDate(int year) {
        LocalDate lower = LocalDate.of(year,1,1);
        LocalDate upper = LocalDate.of(year,12,31);

        return bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(lower,upper)
                .stream().map(Book::getTitle).toList();
    }

    @Override
    public List<String> findBookReleaseDate(LocalDate localDate) {
        return bookRepository.findBookByReleaseDateBefore(localDate)
                .stream().map(book -> String.format("%s %s %.2f",book.getTitle(),book.getEditionType(),book.getPrice())).toList();
    }

    @Override
    public List<String> findBookContainGivenString(String input) {
        return bookRepository.findAllByTitleContaining(input)
                .stream().map(Book::getTitle).toList();
    }

    @Override
    public List<String> findBookTitlesLastNameStartWith(String inputString) {
        return bookRepository.findAllByAuthor_LastNameStartsWith(inputString)
                .stream().map(book -> String.format("%s %s %s",book.getTitle(),book.getAuthor().getFirstName(),book.getAuthor().getLastName())).toList();
    }

    @Override
    public int findCountBooksWithGivenNumber(int inputGivenNumber) {
    return bookRepository.countOfBookWithTitleLengthMoreThan(inputGivenNumber);
    }

    @Override
    public int changePrice(long bookId) {
       return bookRepository.changeBookByPriceId(bookId);
    }

    @Override
    public int increaseCopiesByDate(LocalDate localDate,int copies) {
        int affectedRows = bookRepository.updateCopiesByReleaseDate(copies,localDate);

        return affectedRows * copies;
    }

    @Override
    public int bookIncrease1212(String date, int amount) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);

        return this.bookRepository.addCopiesToBookAfter(localDate,amount);
    }

    @Override
    public BookSummary getInformationAboutTitle(String title) {
        return this.bookRepository.findSummaryForTitle(title);
    }

    @Override
    public List<String> getReducedBooks(String titleInpit) {
        return this.bookRepository.findReducedBook(titleInpit)
                .stream().map(book -> String.format("%s %.2f",book.getTitle(),book.getPrice())).toList();
    }

    @Override
    public int deleteCopiesLessThanAmount(int amount) {
        return this.bookRepository.deleteByCopiesLessThan(amount);
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
