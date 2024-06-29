package com.librarysys.repository;

import com.librarysys.entity.Borrowing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BorrowingRepository extends MongoRepository<Borrowing, String>
{
    Borrowing save(Borrowing borrowing);
}
