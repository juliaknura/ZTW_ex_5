package com.example.Lab5.controller;

import com.example.Lab5.service.ILibraryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class LibraryController {

    @Autowired
    private final ILibraryService libraryService;

//    @RequestMapping("/")
//    public String hello() {
//        return "Hello! I'm running";
//    }

    @GetMapping(value = "/get/booksCopies")
    public ResponseEntity<Object> getBooksCopies(){
        return new ResponseEntity<>(libraryService.getBooksCopiesRecords(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/lendingRegister")
    public ResponseEntity<Object> getLendingRegisterRecords(){
        return new ResponseEntity<>(libraryService.getLendingRegister(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/readers")
    public ResponseEntity<Object> getReaders(){
        return new ResponseEntity<>(libraryService.getReaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/borrow/book/{bookID}/reader/{readerID}")
    public ResponseEntity<Object> borrowBook(@PathVariable("bookID") int bookID, @PathVariable("readerID") int readerID) {
        return switch (libraryService.borrowBook(bookID, readerID)) {
            case SUCCESS -> new ResponseEntity<>("The book has been successfully borrowed", HttpStatus.OK);
            case BOOK_UNAVAILABLE -> new ResponseEntity<>("The book has no available copies for borrowing.", HttpStatus.CONFLICT);
            case BOOK_NOT_FOUND -> new ResponseEntity<>("The book is not available in the library.", HttpStatus.NOT_FOUND);
            case READER_HAS_NO_BORROW_RIGHT -> new ResponseEntity<>("The reader does not have permissions to borrow books.", HttpStatus.FORBIDDEN);
            case READER_NOT_FOUND -> new ResponseEntity<>("The reader is not registered", HttpStatus.NOT_FOUND);
        };
    }

    @PutMapping(value = "/return/book/{bookID}/reader/{readerID}")
    public ResponseEntity<String> returnBook(@PathVariable("bookID") int bookID, @PathVariable("readerID") int readerID) {
        return switch (libraryService.returnBook(bookID, readerID)) {
            case SUCCESS -> new ResponseEntity<>("The book has been successfully returned",HttpStatus.OK);
            case BOOK_NOT_FOUND -> new ResponseEntity<>("The book is not available in the library.", HttpStatus.NOT_FOUND);
            case READER_NOT_FOUND -> new ResponseEntity<>("The reader is not registered", HttpStatus.NOT_FOUND);
            case BOOK_NOT_BORROWED -> new ResponseEntity<>("The book was not borrowed",HttpStatus.CONFLICT);
        };
    }
}
