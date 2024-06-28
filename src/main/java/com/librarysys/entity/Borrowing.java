package com.librarysys.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "borrowing")
public class Borrowing
{
    @Id
    private int borrowingId;
    @DBRef
    private int bookId;
    @DBRef
    private int userId;
    private LocalDate startTime;
    private LocalDate endTime;

    public int getBorrowingId()
    {
        return borrowingId;
    }

    public int getBookId()
    {
        return bookId;
    }

    public void setBookId(int bookId)
    {
        this.bookId = bookId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public LocalDate getStartTime()
    {
        return startTime;
    }

    public void setStartTime(LocalDate startTime)
    {
        this.startTime = startTime;
    }

    public LocalDate getEndTime()
    {
        return endTime;
    }

    public void setEndTime(LocalDate endTime)
    {
        this.endTime = endTime;
    }
}
