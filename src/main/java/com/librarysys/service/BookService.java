package com.librarysys.service;

import com.librarysys.entity.Book;
import com.librarysys.repository.BookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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
        String bookCopyId = generateBookCopyId();
        newBook.setBookCopyId(bookCopyId);
        return bookRepository.save(newBook);
    }

    public List<Book> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) return null;
        else return books;
    }

    public void updateStatus(String id, String status){
        Book book = bookRepository.findByBookCopyId(id);
        if(status.equals("free")){
            book.setStatus(Book.BookStatus.free);
        }
        else book.setStatus(Book.BookStatus.borrowed);
        bookRepository.save(book);
    }

    public ObjectId findBookByBookCopyId(String bookCopyId){
        Book book = bookRepository.findByBookCopyId(bookCopyId);
        if(book != null){
            return book.getBookId();
        }
        else return null;
    }

    public String generateBookCopyId(){
        boolean doesExist= false;
        String generatedString = "";
        if(!doesExist)
        {
            int leftLimit = 48; // numeral '0'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 15;
            Random random = new Random();

            generatedString = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

        }
        if(bookRepository.existsByBookCopyId(generatedString)) doesExist=true;
        else{
            doesExist = false;
        }
        if(doesExist) generateBookCopyId();
        System.out.println("Generated bookCopyId: " + generatedString);
        return generatedString;
    }
}
