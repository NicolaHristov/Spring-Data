package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    //6.	Authors Search
    //Write a program that prints the names of those authors, whose first name ends with a given string
    List<Author>findAuthorByFirstNameEndsWith(String endsName);

    @Query("SELECT a.firstName,a.lastName,SUM(a.books.size) FROM Author  AS a JOIN Book AS b")
    List<Author> findAllByAuthorswWithJpql();

}
