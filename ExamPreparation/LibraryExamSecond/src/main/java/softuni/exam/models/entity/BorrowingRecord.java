package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord extends BaseEntity{

    @Column(name = "borrow_date",nullable = false)
    private LocalDate borrowDate;
    @Column(name = "return_date",nullable = false)
    private LocalDate returnDate;
    private String remarks;

    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private LibraryMember libraryMember;

    @ManyToOne(optional = false)
    private Book book;

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }



    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
