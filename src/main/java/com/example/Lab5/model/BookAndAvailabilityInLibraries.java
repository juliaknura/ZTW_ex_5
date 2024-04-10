package com.example.Lab5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class BookAndAvailabilityInLibraries {
    private int id;
    private String title;
    private String subtitle;
    private List<Author> authors;
    private int yearOfPublication;
    private int copiesNumber;
    private int currentAvailable;

    public static BookAndAvailabilityInLibraries fromBook(Book book) {
        return new BookAndAvailabilityInLibraries(
                book.getId(),
                book.getTitle(),
                book.getSubtitle(),
                book.getAuthors(),
                book.getYearOfPublication(),
                0,
                0);
    }

    public void setAvailabilityInLibraries(BooksCopiesRecords booksCopiesRecords) {
        copiesNumber = booksCopiesRecords.getCopiesNumber();
        currentAvailable = booksCopiesRecords.getCurrentAvailable();
    }
}
