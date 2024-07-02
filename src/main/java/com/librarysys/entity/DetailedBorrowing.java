package com.librarysys.entity;

public class DetailedBorrowing
{
    private Borrowing borrowing;
    private Book book;

    public DetailedBorrowing(Borrowing borrowing, Book book){
        this.borrowing = borrowing;
        this.book = book;
    }

    public Borrowing getBorrowing()
    {
        return borrowing;
    }

    public void setBorrowing(Borrowing borrowing)
    {
        this.borrowing = borrowing;
    }


    public Book getBook()
    {
        return book;
    }

    public void setBook(Book book)
    {
        this.book = book;
    }
}
