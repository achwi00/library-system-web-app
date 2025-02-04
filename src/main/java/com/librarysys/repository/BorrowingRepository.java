package com.librarysys.repository;

import com.librarysys.entity.Book;
import com.librarysys.entity.Borrowing;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BorrowingRepository extends MongoRepository<Borrowing, String>
{
    Borrowing save(Borrowing borrowing);

    Borrowing findByUserIdAndBookIdAndEndTime(ObjectId userId, ObjectId bookId, LocalDate endTime);

    List<Borrowing> findAllByUserId(ObjectId userId);

    void deleteAllByBookId(ObjectId bookId);

    List<Borrowing> findAllByUserIdAndEndTime(ObjectId userId, LocalDate endTime);
    Borrowing findByBookIdAndStatus(ObjectId bookId, Borrowing.BorrowingStatus status);

}
