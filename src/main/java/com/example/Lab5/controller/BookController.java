package com.example.Lab5.controller;

import com.example.Lab5.model.Book;
import com.example.Lab5.service.IBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    IBooksService booksService;

    @RequestMapping(value = "/get/books", method = RequestMethod.GET)
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(booksService.getBooks(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/books/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBook(@PathVariable("id") int id){
        var found = booksService.getBook(id);
        return found.<ResponseEntity<Object>>map(book -> new ResponseEntity<>(book, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>("Book with id " + id + " not found", HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/update/books/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBook(@PathVariable("id") int id, @RequestBody Book book) {
        if(booksService.updateBook(id,book))
            return new ResponseEntity<>("Book updated successfully",HttpStatus.OK);
        else
            return new ResponseEntity<>("Book with id " + id + " not found",HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/delete/books/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id) {
        if(booksService.deleteBook(id))
            return new ResponseEntity<>("Book deleted successfully",HttpStatus.OK);
        else
            return new ResponseEntity<>("Book with id " + id + " not found",HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/create/books", method = RequestMethod.PUT)
    public ResponseEntity<String> createBook(@RequestBody Book book)
    {
        if(booksService.createBook(book))
            return new ResponseEntity<>("Book created successfully",HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Error while creating the book",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/brew/coffee", method = RequestMethod.GET)
    public ResponseEntity<String> brewCoffee() {
        return new ResponseEntity<>("The server refuses the attempt to brew coffee with a teapot.",HttpStatus.I_AM_A_TEAPOT);
    }
}
