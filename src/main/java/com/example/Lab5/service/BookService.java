package com.example.Lab5.service;

import com.example.Lab5.model.Author;
import com.example.Lab5.model.Book;
import com.example.Lab5.repository.IMemoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService implements IBooksService {

    @Autowired
    private final IMemoryRepository repository;

    @Override
    public Collection<Book> getBooks() {
        return repository.getBooks();
    }

    @Override
    public Optional<Book> getBook(int id) {
        return repository.getBooks()
                .stream()
                .filter(b-> b.getId() == id)
                .findAny();
    }

    @Override
    public boolean updateBook(int id, Book book){
        var foundBook = getBook(id);
        if(foundBook.isEmpty())
        {
            return false;
        }
        else
        {
            var editedBook = foundBook.get();
            editedBook.setTitle(book.getTitle());
            editedBook.setAuthors(book.getAuthors());
            editedBook.setSubtitle(book.getSubtitle());
            editedBook.setYearOfPublication(book.getYearOfPublication());
            return true;
        }
    }

    @Override
    public boolean deleteBook(int id){
        var foundBook = getBook(id);
        if(foundBook.isEmpty())
            return false;
        else
        {
            repository.getBooks().remove(foundBook.get());
            return true;
        }
    }

    private int getNextBookID() {
        return repository.getBooks()
                .stream()
                .max(Comparator.comparingInt(Book::getId))
                .map(Book::getId)
                .orElse(0) + 1;
    }

    private List<Author> createAuthors(List<Author> authors) {
        return authors.stream()
                .map(author -> repository.getAuthors()
                        .stream()
                        .filter(it -> it.equals(author))
                        .findFirst()
                        .orElseGet(() -> {
                            author.setId(repository.getAuthors().size() + 1);
                            repository.getAuthors().add(author);
                            return author;
                        })
                ).toList();
    }

    @Override
    public boolean createBook(Book book){
        repository.getBooks().add(
                new Book(
                        getNextBookID(),
                        book.getTitle(),
                        book.getSubtitle(),
                        createAuthors(book.getAuthors()),
                        book.getYearOfPublication()
                )
        );
        return true;
    }
}
