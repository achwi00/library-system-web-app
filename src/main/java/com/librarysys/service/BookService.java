package com.librarysys.service;

import com.librarysys.entity.Book;
import com.librarysys.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(String title, String author, Book.BookStatus status, String publisher) {
        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setStatus(status);
        newBook.setPublisher(publisher);
        return bookRepository.save(newBook);
    }
}
