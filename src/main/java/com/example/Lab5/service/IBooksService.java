package com.example.Lab5.service;

import com.example.Lab5.model.Book;
import java.util.Collection;
import java.util.Optional;

public interface IBooksService {
    Collection<Book> getBooks();
    Optional<Book> getBook(int id);
    boolean updateBook(int id, Book book);
    boolean deleteBook(int id);
    boolean createBook(Book book);


}
