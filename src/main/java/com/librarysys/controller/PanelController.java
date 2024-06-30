package com.librarysys.controller;

import com.librarysys.entity.Book;
import com.librarysys.service.BookService;
import com.librarysys.service.LibUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                               @RequestParam("publisher") String publisher,
                               RedirectAttributes redirectAttributes){
        bookService.addBook(title, author, Book.BookStatus.free, publisher);
        return "Book added";
    }
}
