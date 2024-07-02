package com.librarysys.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.librarysys.entity.Book;
import com.librarysys.entity.Borrowing;
import com.librarysys.entity.DetailedBorrowing;
import com.librarysys.entity.LibUser;
import com.librarysys.service.BookService;
import com.librarysys.service.BorrowingService;
import com.librarysys.service.LibUserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/all-readers")
    public List<LibUser> getAllReaders()
    {
        return libUserService.getAllReaders();
    }

    @PostMapping("/all-books/borrowing")
    public String createBorrowing(@RequestParam("cardNum") String cardNum,
                                  @RequestParam("bookId") String bookCopyId)
    {
        ObjectId userId = libUserService.findUserByCard(cardNum);
        ObjectId bookId = bookService.findBookByBookCopyId(bookCopyId);
        if(userId != null){
            borrowingService.createBorrowing(bookId, userId);
            bookService.updateStatus(bookCopyId, "borrowed");
            return "A booking was made.";
        }
        return "Invalid card number." ;
    }

    @PostMapping("/all-books/return-book")
    public String returnBook(@RequestParam("bookCopyId") String bookCopyId)
    {
        bookService.updateStatus(bookCopyId, "free");
        ObjectId bookId = bookService.findBookByBookCopyId(bookCopyId);
        borrowingService.endBorrowing(bookId);
        return "Returned";
    }

    @PostMapping("/all-readers/delete")
    public String deleteUser(@RequestParam("cardNum") String cardNumber)
    {
        libUserService.deleteUserByCard(cardNumber);
        return "Deleted";
    }

    @PostMapping("/all-books/delete-book")
    public String deleteBook(@RequestParam("bookCopyId") String bookCopyId){
        ObjectId bookId = bookService.findBookByBookCopyId(bookCopyId);
        bookService.deleteBook(bookCopyId);
        borrowingService.deleteAllByBook(bookId);
        return "Deleted";
    }
    @PostMapping("/all-readers/current-borrow")
    public List<DetailedBorrowing> allBorrowingsOfReader(@RequestParam("cardNumber") String cardNumber){
        ObjectId userId = libUserService.findUserByCard(cardNumber);
        return borrowingService.findAllCurrentUserBorrowings(userId);
    }
    @PostMapping("/all-readers/history")
    public List<DetailedBorrowing> borrowingHistory(@RequestParam("cardNumber") String cardNumber){
        ObjectId userId = libUserService.findUserByCard(cardNumber);
        return borrowingService.findBorrowingHistory(userId);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam String sessionKey){
        ObjectId userId = libUserService.findIdBySessionKey(sessionKey);
        libUserService.updateSessionKey(userId, null);
        return ResponseEntity.ok().build();
    }
}
