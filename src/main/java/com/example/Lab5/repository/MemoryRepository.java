package com.example.Lab5.repository;

import com.example.Lab5.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
@Getter
@AllArgsConstructor
public class MemoryRepository implements IMemoryRepository{

    private final static List<Author> authors = new ArrayList<>();
    private final static List<Book> booksRepo = new ArrayList<>();
    private final static List<LendingRegisterRecord> lendingRegister = new ArrayList<>();
    private final static List<BooksCopiesRecords> booksCopies = new ArrayList<>();
    private final static List<Reader> readers = new ArrayList<>();

    static {
        authors.add(new Author(1, "Scott","Meyers"));
        authors.add(new Author(2, "Rafał", "Świdziński"));
        authors.add(new Author(3, "Richard", "Lyons"));
        authors.add(new Author(4, "Marian", "Gewert"));
        authors.add(new Author(5, "Zbigniew", "Skoczylas"));
        authors.add(new Author(6, "Eric", "Evans"));

        readers.add(new Reader(1, "Xardas", "Necromancer"));
        readers.add(new Reader(2, "Bezi", "Mienny"));
        readers.add(new Reader(3, "Zygmunt", "Dostępowiec"));

        booksRepo.add(new Book(1, "Effective Modern C++","42 Specific Ways to Improve Your Use of C++11 and C++14", List.of(authors.get(0)), 2015));
        booksRepo.add(new Book(2, "Modern CMake for C++","Discover a better approach to building, testing, and packaging your software", List.of(authors.get(1)),2022));
        booksRepo.add(new Book(3, "Understanding Digital Signal Processing","3rd Edition", List.of(authors.get(2)),2010));
        booksRepo.add(new Book(4,"Analiza matematyczna 1","Definicje, twierdzenia, wzory", List.of(authors.get(3), authors.get(4)), 2023));
        booksRepo.add(new Book(5,"Analiza matematyczna 1","Przykłady i zadania", List.of(authors.get(3), authors.get(4)),2022));
        booksRepo.add(new Book(6,"Domain-Driven Design","Tackling Complexity in the Heart of Software", List.of(authors.get(5)),2003));

        booksCopies.add(new BooksCopiesRecords(1, booksRepo.get(0).getId(), 2, 2));
        booksCopies.add(new BooksCopiesRecords(2, booksRepo.get(1).getId(), 1, 1));
        booksCopies.add(new BooksCopiesRecords(3, booksRepo.get(2).getId(), 1, 1));
        booksCopies.add(new BooksCopiesRecords(4, booksRepo.get(3).getId(), 5, 3));
        booksCopies.add(new BooksCopiesRecords(5, booksRepo.get(4).getId(), 6, 6));
        booksCopies.add(new BooksCopiesRecords(6, booksRepo.get(5).getId(), 2, 0));

        lendingRegister.add(new LendingRegisterRecord(1,
                booksRepo.get(0).getId(),
                readers.get(0).id(),
                LocalDate.of(2024, 1, 12),
                LocalDate.of(2024, 2, 12),
                LocalDate.of(2024, 2, 9)));
        lendingRegister.add(new LendingRegisterRecord(2,
                booksRepo.get(1).getId(),
                readers.get(1).id(),
                LocalDate.of(2024, 1, 20),
                LocalDate.of(2024, 2, 20),
                LocalDate.of(2024, 2, 19)));
        lendingRegister.add(new LendingRegisterRecord(3,
                booksRepo.get(3).getId(),
                readers.get(1).id(),
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 5, 1),
                null));
        lendingRegister.add(new LendingRegisterRecord(4,
                booksRepo.get(3).getId(),
                readers.get(2).id(),
                LocalDate.of(2024, 4, 2),
                LocalDate.of(2024, 5, 2),
                null));
        lendingRegister.add(new LendingRegisterRecord(5,
                booksRepo.get(5).getId(),
                readers.get(2).id(),
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 4, 1),
                null));
        lendingRegister.add(new LendingRegisterRecord(6,
                booksRepo.get(5).getId(),
                readers.get(1).id(),
                LocalDate.of(2024, 4, 5),
                LocalDate.of(2024, 5, 5),
                null));
    }

    @Override
    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public List<Book> getBooks() {
        return booksRepo;
    }

    @Override
    public List<LendingRegisterRecord> getLendingRegister() {
        return lendingRegister;
    }

    @Override
    public List<BooksCopiesRecords> getBooksCopies() {
        return booksCopies;
    }

    @Override
    public List<Reader> getReaders() {
        return readers;
    }

    @Override
    public List<BookAndAvailabilityInLibraries> getFullBooksInfo() {
        var bookAndAvailability = new HashMap<Integer, BookAndAvailabilityInLibraries>();
        for (var book : booksRepo) {
            bookAndAvailability.put(book.getId(), BookAndAvailabilityInLibraries.fromBook(book));
        }
        for (var bookCopies: booksCopies) {
            if (bookAndAvailability.containsKey(bookCopies.getBookID()))
                bookAndAvailability.get(bookCopies.getBookID()).setAvailabilityInLibraries(bookCopies);
        }

        return bookAndAvailability.values().stream().toList();
    }
}
