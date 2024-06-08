package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    private final BufferedReader bufferedReader;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService, BufferedReader bufferedReader) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        //printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
     //   printAllAuthorsAndNumberOfTheirBooks();
//        pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        System.out.println("Select exercise:");
        int exNum = Integer.parseInt(bufferedReader.readLine());

        switch (exNum){
            case 1 -> findAllBooksByAgeRestriction();
            case 2 -> findTheTitlesGoldenBooks();
            case 3 -> findBooksByPrice();
            case 4 -> findBookTitleNotReleaseDate();
            case 5 -> bookReleaseBeforeDate();
            case 6 -> authorsSearche();
            case 7 -> bookSearche();
            case 8 -> bookTitlesSearche();
            case 9 -> countBooks();
            case 10 -> totalBooksCopies();
            case 11 -> reducedBooks();
            case 111 -> reducedBooksAlone();
            case 12 -> increaseBookCopies();
            case 13 -> removeBooks();
            case 1212 -> bookIncreaseCopies1212();
            case 1010 -> aloneTotalBooksCopiesWithJPQLQueryis();
            case 99 -> testProcedure();

        }

    }

    private void removeBooks() throws IOException {
        int amount = Integer.parseInt(bufferedReader.readLine());
        this.bookService.deleteCopiesLessThanAmount(amount);
    }

    private void reducedBooksAlone() throws IOException {
        String titleInpit = bufferedReader.readLine();
        this.bookService.getReducedBooks(titleInpit).forEach(System.out::println);
    }

    private void reducedBooks() throws IOException {
        String title = bufferedReader.readLine();
       BookSummary summary = this.bookService.getInformationAboutTitle(title);
//        System.out.printf(String.format("%s %s %s %.2f",summary.getTitle(),summary.getEditionType(),summary.getAgeRestriction(),summary.getPrice()));
        System.out.println(summary.getTitle() + " " + summary.getEditionType() + " "+ summary.getAgeRestriction() +" "+ summary.getPrice());
    }

    private void bookIncreaseCopies1212() throws IOException {
        String date = bufferedReader.readLine();
        int amount = Integer.parseInt(bufferedReader.readLine());
        int booksUpdated = bookService.bookIncrease1212(date,amount);
//        LocalDate localDate = LocalDate.parse(date,new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd MMM yyyy").toFormatter(Locale.US));
        System.out.printf("%d books are released after %s so total of %d book copies were added",booksUpdated,date,amount*booksUpdated);
    }

    private void increaseBookCopies() throws IOException {
//        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd MMM yyyy"));
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
//        LocalDate after = LocalDate.parse(bufferedReader.readLine(),dateTimeFormatter);
        String date = bufferedReader.readLine();
        LocalDate localDate = LocalDate.parse(date,new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd MMM yyyy").toFormatter(Locale.US));
        int copies = Integer.parseInt(bufferedReader.readLine());
        System.out.println(bookService.increaseCopiesByDate(localDate, copies));

    }

    private void testProcedure() {
       bookService.changePrice(1L);
    }

    private void aloneTotalBooksCopiesWithJPQLQueryis() {
        authorService.findAllAuthorsWithJPQL().forEach(System.out::println);
    }

    private void totalBooksCopies() {
        authorService.findAllAuthorsAndTheirCopies().forEach(System.out::println);
    }

    private void countBooks() throws IOException {
        int inputGivenNumber = Integer.parseInt(bufferedReader.readLine());
        System.out.println(bookService.findCountBooksWithGivenNumber(inputGivenNumber));
    }

    private void bookTitlesSearche() throws IOException {
        String inputString = bufferedReader.readLine();
        bookService.findBookTitlesLastNameStartWith(inputString).forEach(System.out::println);
    }

    private void bookSearche() throws IOException {
        String input = bufferedReader.readLine();
        bookService.findBookContainGivenString(input).forEach(System.out::println);
    }

    private void authorsSearche() throws IOException {
        String input = bufferedReader.readLine();
        authorService.authorSearcheFirstNameEndsWith(input).forEach(System.out::println);

    }

    private void bookReleaseBeforeDate() throws IOException {
        LocalDate localDate = LocalDate.parse(bufferedReader.readLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        bookService.findBookReleaseDate(localDate).forEach(System.out::println);
    }

    private void findBookTitleNotReleaseDate() throws IOException {
        int year = Integer.parseInt(bufferedReader.readLine());

        bookService.findAllBookTitleNotReleaseDate(year).forEach(System.out::println);
    }

    private void findBooksByPrice() {
        bookService.findBooksByPriceLowerThanHigherThan().forEach(System.out::println);
    }

    private void findTheTitlesGoldenBooks() {
        //Write a program that prints the titles of the golden edition books, which have less than 5000 copies.
        bookService.findTheTitlesOfGoldenBooksLessThan5000().forEach(System.out::println);
    }

    private void findAllBooksByAgeRestriction() throws IOException {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(bufferedReader.readLine().toUpperCase());
        bookService.findAllBookAgeRestriction(ageRestriction).forEach(System.out::println);
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
