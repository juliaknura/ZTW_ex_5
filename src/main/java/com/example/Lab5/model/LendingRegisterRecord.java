package com.example.Lab5.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class LendingRegisterRecord {

    private int id;
    private final int bookID;
    private final int readerID;
    private final LocalDate bookBorrowedDate;
    private final LocalDate expectedBookReturnDate;
    private LocalDate bookReturnDate;

}
