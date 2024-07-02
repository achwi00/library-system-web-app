package com.librarysys.service;

import com.librarysys.entity.Book;
import com.librarysys.entity.Borrowing;
import com.librarysys.entity.LibUser;
import com.librarysys.repository.BorrowingRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class BorrowingService
{
    @Autowired
    private BorrowingRepository borrowingRepository;

    public Borrowing createBorrowing(ObjectId bookId, ObjectId userId) {

        Borrowing borrowing = new Borrowing();
        borrowing.setBookId(bookId);
        borrowing.setUserId(userId);
        borrowing.setStartTime(LocalDate.now());
        System.out.println("UserId: for borrowing " + borrowing.getUserId());
        return borrowingRepository.save(borrowing);
    }

    public Borrowing endBorrowing(ObjectId bookId, ObjectId userId){
        Borrowing borrowing = borrowingRepository.findByUserIdAndBookIdAndEndTime(userId, bookId, null);
        if(borrowing != null){
            borrowing.setEndTime(LocalDate.now());
            return borrowingRepository.save(borrowing);
        }
        else return null;
    }

    public void deleteAllByBook(ObjectId bookId){
        borrowingRepository.deleteAllByBookId(bookId);
    }

    public List<Borrowing> findAllCurrentUserBorrowings(ObjectId userId){
        return borrowingRepository.findAllByUserIdAndEndTime(userId, null);
    }

    public List<Borrowing> findAllPreviousUserBorrowings(ObjectId userId){
        List<Borrowing> previousBorrowings = borrowingRepository.findAllByUserId(userId);
        previousBorrowings.removeIf(Objects::isNull);
        return previousBorrowings;
    }
}
