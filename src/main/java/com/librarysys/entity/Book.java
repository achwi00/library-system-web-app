package com.librarysys.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book")
public class Book
{
    @Id
    private ObjectId bookId;
    private String author;
    private String title;
    private BookStatus status;
    private String publisher;
    private String bookCopyId; //physical library card number

    public enum BookStatus{
        free,
        borrowed;
    }

    public String getBookCopyId()
    {
        return bookCopyId;
    }

    public void setBookCopyId(String bookCopyId)
    {
        this.bookCopyId = bookCopyId;
    }

    public ObjectId getBookId()
    {
        return bookId;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public BookStatus getStatus()
    {
        return status;
    }

    public void setStatus(BookStatus status)
    {
        this.status = status;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }
}
