package com.librarysys.service;

import com.librarysys.entity.Book;
import com.librarysys.entity.Borrowing;
import com.librarysys.entity.DetailedBorrowing;
import com.librarysys.entity.LibUser;
import com.librarysys.repository.BookRepository;
import com.librarysys.repository.BorrowingRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
public class BorrowingService
{
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    public Borrowing createBorrowing(ObjectId bookId, ObjectId userId) {
        Borrowing borrowing = new Borrowing();
        borrowing.setBookId(bookId);
        borrowing.setUserId(userId);
        borrowing.setStartTime(LocalDate.now());
        borrowing.setStatus(Borrowing.BorrowingStatus.ONGOING);
        borrowing.setEndTime(null);
        System.out.println("UserId: for borrowing " + borrowing.getUserId());
        return borrowingRepository.save(borrowing);
    }

    public Borrowing endBorrowing(ObjectId bookId){
       Borrowing borrowing = borrowingRepository.findByBookIdAndStatus(bookId, Borrowing.BorrowingStatus.ONGOING);
        if(borrowing != null){
            borrowing.setStatus(Borrowing.BorrowingStatus.FINISHED);
            borrowing.setEndTime(LocalDate.now());
            System.out.println(borrowing.getEndTime());
            return borrowingRepository.save(borrowing);
        }
        else return null;
    }

    public void deleteAllByBook(ObjectId bookId){
        borrowingRepository.deleteAllByBookId(bookId);
    }

    public List<DetailedBorrowing> findAllCurrentUserBorrowings(ObjectId userId){
        List<Borrowing> borrowingsClear = borrowingRepository.findAllByUserIdAndEndTime(userId, null);
        List<DetailedBorrowing> detailedBorrowings = new LinkedList<>();
        for(Borrowing borrowing : borrowingsClear){
            DetailedBorrowing detailedBorrowing = new DetailedBorrowing(borrowing, bookRepository.findByBookId(borrowing.getBookId()));
            detailedBorrowings.add(detailedBorrowing);
        }
        return detailedBorrowings;
    }

    public List<DetailedBorrowing> findBorrowingHistory(ObjectId userId){
        List<Borrowing> previousBorrowings = borrowingRepository.findAllByUserId(userId);
        List<DetailedBorrowing> detailedBorrowings = new LinkedList<>();
        for(Borrowing borrowing : previousBorrowings){
            if (borrowing.getEndTime() != null){
                DetailedBorrowing detailedBorrowing = new DetailedBorrowing(borrowing, bookRepository.findByBookId(borrowing.getBookId()));
                detailedBorrowings.add(detailedBorrowing);
            }

        }
        return detailedBorrowings;
    }
}
