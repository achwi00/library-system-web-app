package com.librarysys.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "borrowing")
public class Borrowing
{
    @Id
    private ObjectId borrowingId;
    @Field("bookId")
    private ObjectId bookId;
    @Field("userId")
    private ObjectId userId;
    private LocalDate startTime;
    private LocalDate endTime;

    public ObjectId getBorrowingId()
    {
        return borrowingId;
    }

    public ObjectId getBookId()
    {
        return bookId;
    }

    public void setBookId(ObjectId bookId)
    {
        this.bookId = bookId;
    }

    public ObjectId getUserId()
    {
        return userId;
    }

    public void setUserId(ObjectId userId)
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
