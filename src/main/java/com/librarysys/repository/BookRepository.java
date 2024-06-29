package com.librarysys.repository;

import com.librarysys.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String>
{
    Book save(Book book);
}
