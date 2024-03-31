package com.example.Lab5.service;

import com.example.Lab5.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService implements IBooksService{
    private static List<Book> booksRepo = new ArrayList<>();

    static {
        booksRepo.add(new Book(1, "Effective Modern C++","42 Specific Ways to Improve Your Use of C++11 and C++14", Arrays.asList("Scott Meyers"), 2015));
        booksRepo.add(new Book(2, "Modern CMake for C++","Discover a better approach to building, testing, and packaging your software", Arrays.asList("Rafał Świdziński"),2022));
        booksRepo.add(new Book(3, "Understanding Digital Signal Processing","3rd Edition", Arrays.asList("Richard G. Lyons"),2010));
        booksRepo.add(new Book(4,"Analiza matematyczna 1","Definicje, twierdzenia, wzory", Arrays.asList("Marian Gewert","Zbigniew Skoczylas"),2023));
        booksRepo.add(new Book(5,"Analiza matematyczna 1","Przykłady i zadania", Arrays.asList("Marian Gewert","Zbigniew Skoczylas"),2022));
        booksRepo.add(new Book(6,"Domain-Driven Design","Tackling Complexity in the Heart of Software", Arrays.asList("Eric Evans"),2003));
    }

    @Override
    public Collection<Book> getBooks() {
        return booksRepo;
    }

    @Override
    public Optional<Book> getBook(int id) {
        return booksRepo.stream().filter(b-> b.getId() == id).findAny();
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
            booksRepo.remove(foundBook.get());
            return true;
        }
    }

    @Override
    public boolean createBook(Book book){
        var bookWithMaxId = booksRepo.stream().max(Comparator.comparingInt(Book::getId));
        int validId;
        if(bookWithMaxId.isEmpty())
            validId = 1;
        else
            validId = bookWithMaxId.get().getId()+1;

        Book createdBook = new Book(validId,book.getTitle(),book.getSubtitle(),book.getAuthors(),book.getYearOfPublication());
        booksRepo.add(createdBook);
        return true;
    }
}
