package com.example.Lab5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BooksCopiesRecords {

    private int id;
    private final int bookID;
    private int copiesNumber;
    private int currentAvailable;
}
