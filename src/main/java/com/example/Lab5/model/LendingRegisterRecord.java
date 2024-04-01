package com.example.Lab5.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class LendingRegisterRecord {

    private int id;
    private final Book book;
    private final Reader reader;
    private final LocalDate bookBorrowedDate;
    private final LocalDate expectedBookReturnDate;
    private final LocalDate bookReturnDate;

}
