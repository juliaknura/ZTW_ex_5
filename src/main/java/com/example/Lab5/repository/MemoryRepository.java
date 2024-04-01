package com.example.Lab5.repository;

import com.example.Lab5.model.Author;
import com.example.Lab5.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
@AllArgsConstructor
public class MemoryRepository implements IMemoryRepository{

    private final static List<Author> authors = new ArrayList<>();
    private final static List<Book> booksRepo = new ArrayList<>();

    static {
        authors.add(new Author(1, "Scott","Meyers"));
        authors.add(new Author(2, "Rafał", "Świdziński"));
        authors.add(new Author(3, "Richard", "Lyons"));
        authors.add(new Author(4, "Marian", "Gewert"));
        authors.add(new Author(5, "Zbigniew", "Skoczylas"));
        authors.add(new Author(6, "Eric", "Evans"));
        booksRepo.add(new Book(1, "Effective Modern C++","42 Specific Ways to Improve Your Use of C++11 and C++14", List.of(authors.get(0)), 2015));
        booksRepo.add(new Book(2, "Modern CMake for C++","Discover a better approach to building, testing, and packaging your software", List.of(authors.get(1)),2022));
        booksRepo.add(new Book(3, "Understanding Digital Signal Processing","3rd Edition", List.of(authors.get(2)),2010));
        booksRepo.add(new Book(4,"Analiza matematyczna 1","Definicje, twierdzenia, wzory", List.of(authors.get(3), authors.get(4)), 2023));
        booksRepo.add(new Book(5,"Analiza matematyczna 1","Przykłady i zadania", List.of(authors.get(3), authors.get(4)),2022));
        booksRepo.add(new Book(6,"Domain-Driven Design","Tackling Complexity in the Heart of Software", List.of(authors.get(5)),2003));
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public List<Book> getBooks() {
        return booksRepo;
    }
}
