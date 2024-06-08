package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookSummary;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllBookAgeRestriction(AgeRestriction ageRestriction);

    List<String> findTheTitlesOfGoldenBooksLessThan5000();

    List<String> findBooksByPriceLowerThanHigherThan();

    List<String> findAllBookTitleNotReleaseDate(int year);

    List<String> findBookReleaseDate(LocalDate localDate);

    List<String> findBookContainGivenString(String input);

    List<String> findBookTitlesLastNameStartWith(String inputString);

    int findCountBooksWithGivenNumber(int inputGivenNumber);

    int changePrice(long bookId);

    int increaseCopiesByDate(LocalDate localDate,int copies);

    int bookIncrease1212(String date, int amount);

    BookSummary getInformationAboutTitle(String title);

    List<String> getReducedBooks(String titleInpit);

    int  deleteCopiesLessThanAmount(int amount);
}
