package com.example.Lab5.repository;

import com.example.Lab5.model.Author;
import com.example.Lab5.model.Book;

import java.util.List;

public interface IMemoryRepository {
    List<Author> getAuthors();
    List<Book> getBooks();
}
