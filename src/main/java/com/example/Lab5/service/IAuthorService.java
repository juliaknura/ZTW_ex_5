package com.example.Lab5.service;

import com.example.Lab5.model.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    List<Author> getAuthors();
    Optional<Author> getAuthor(int id);
    boolean updateAuthor(int id, Author author);
    boolean deleteAuthor(int id);
    boolean createAuthor(Author author);

}
