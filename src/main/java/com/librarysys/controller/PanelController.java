package com.librarysys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.librarysys.entity.Book;
import com.librarysys.service.BookService;
import com.librarysys.service.BorrowingService;
import com.librarysys.service.LibUserService;
import org.bson.types.ObjectId;
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

    @Autowired
    private BorrowingService borrowingService;

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

    @PostMapping("/all-books/borrowing")
    public String createBorrowing(@RequestParam("cardNum") String cardNum,
                                  @RequestParam("bookId") String bookCopyId)
    {
        System.out.println("bookCopyId: " + bookCopyId);
        //create a new borrowing
        ObjectId userId = libUserService.findUserByCard(cardNum);
        System.out.println("Found the user: " + userId);
        ObjectId bookId = bookService.findBookByBookCopyId(bookCopyId);
        System.out.println("found the book: " + bookId);
        if(userId != null){
            borrowingService.createBorrowing(bookId, userId);
            //change the book status
            bookService.updateStatus(bookCopyId, "borrowed");
            return "A booking was made.";
        }
        return "Invalid card number." ;
    }

    @PostMapping("/all-books/return-book")
    public String returnBook(@RequestParam("bookCopyId") String bookCopyId,
                             @RequestParam String sessionKey)
    {
        bookService.updateStatus(bookCopyId, "free");
        ObjectId bookId = bookService.findBookByBookCopyId(bookCopyId);
        ObjectId userId = libUserService.findIdBySessionKey(sessionKey);
        borrowingService.endBorrowing(bookId, userId);
        return "Returned";
    }
}
