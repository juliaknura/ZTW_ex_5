package com.example.Lab5.repository;

import com.example.Lab5.model.*;

import java.util.List;

public interface IMemoryRepository {
    List<Author> getAuthors();
    List<Book> getBooks();
    List<LendingRegisterRecord> getLendingRegister();
    List<BooksCopiesRecords> getBooksCopies();
    List<Reader> getReaders();
}
