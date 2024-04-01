package com.example.Lab5.controller;

import com.example.Lab5.model.Author;
import com.example.Lab5.service.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthorController {

    @Autowired
    private final IAuthorService authorService;

    @RequestMapping(value = "/get/authors", method = RequestMethod.GET)
    public ResponseEntity<Object> getBooks() {
        return new ResponseEntity<>(authorService.getAuthors(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/authors/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBook(@PathVariable("id") int id){
        var found = authorService.getAuthor(id);
        return found.<ResponseEntity<Object>>map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Author with id " + id + " not found", HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/update/authors/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateBook(@PathVariable("id") int id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author)
             ? new ResponseEntity<>("Author updated successfully",HttpStatus.OK)
             : new ResponseEntity<>("Author with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/delete/authors/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id) {
        return authorService.deleteAuthor(id)
            ? new ResponseEntity<>("Author deleted successfully", HttpStatus.OK)
            : new ResponseEntity<>("Author with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/create/authors", method = RequestMethod.PUT)
    public ResponseEntity<String> createBook(@RequestBody Author author)
    {
        return authorService.createAuthor(author)
            ? new ResponseEntity<>("Author created successfully", HttpStatus.CREATED)
            : new ResponseEntity<>("Error while creating the author",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
