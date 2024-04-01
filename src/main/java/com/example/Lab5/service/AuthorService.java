package com.example.Lab5.service;

import com.example.Lab5.model.Author;
import com.example.Lab5.repository.IMemoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public final class AuthorService implements IAuthorService {

    @Autowired
    private final IMemoryRepository repository;

    @Override
    public List<Author> getAuthors() {
        return repository.getAuthors();
    }

    @Override
    public Optional<Author> getAuthor(int id) {
        return repository.getAuthors()
                .stream()
                .filter(author -> author.getId() == id)
                .findFirst();
    }

    @Override
    public boolean updateAuthor(int id, Author author) {
        return repository.getAuthors()
                .stream()
                .filter(foundAuthor -> foundAuthor.getId() == id)
                .peek(foundAuthor -> foundAuthor.setAttributes(author))
                .findFirst()
                .isPresent();
    }

    @Override
    public boolean deleteAuthor(int id) {
        return getAuthor(id).stream()
                .peek(author -> repository.getAuthors().remove(author))
                .peek(author -> repository.getBooks()
                            .forEach(book ->
                                    book.setAuthors(book.getAuthors()
                                            .stream()
                                            .filter(it -> !it.equals(author))
                                            .toList()
                                    )
                            )
                )
                .findFirst()
                .isPresent();
    }

    private int getNextID() {
        return repository.getAuthors()
                .stream()
                .max(Comparator.comparingInt(Author::getId))
                .map(Author::getId)
                .orElse(0) + 1;
    }

    @Override
    public boolean createAuthor(Author author) {
        author.setId(getNextID());
        repository.getAuthors().add(author);
        return true;
    }
}
