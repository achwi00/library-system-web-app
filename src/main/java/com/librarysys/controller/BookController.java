package com.librarysys.controller;

import com.librarysys.entity.Book;
import com.librarysys.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class BookController
{
    @Autowired
    private BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<String> createBook(@RequestParam("author") String author,
                                              @RequestParam("title") String title,
                                             @RequestParam("publisher") String publisher){
        bookService.addBook(title, author, Book.BookStatus.free, publisher);
        return ResponseEntity.ok("Added a book");
    }
}
