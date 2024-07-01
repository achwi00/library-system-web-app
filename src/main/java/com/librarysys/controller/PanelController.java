package com.librarysys.controller;

import com.librarysys.entity.Book;
import com.librarysys.service.BookService;
import com.librarysys.service.LibUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/panel")
public class PanelController
{
    @Autowired
    private LibUserService libUserService;

    @Autowired
    private BookService bookService;

    @PostMapping("/add-book")
    public String addBook(@RequestParam("author") String author,
                               @RequestParam("title") String title,
                               @RequestParam("publisher") String publisher){
        bookService.addBook(title, author, Book.BookStatus.free, publisher);
        return "Book added";
    }

    @PostMapping("/add-user")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("surname") String surname,
                          @RequestParam("cardNum") String cardNum){
        libUserService.addUser(name, surname, "customer", cardNum);
        return "User added";
    }

    @GetMapping("/all-books")
    public List<Book> getAllBooks()
    {
        List<Book> books = bookService.getAllBooks();
        return books;
    }
}
