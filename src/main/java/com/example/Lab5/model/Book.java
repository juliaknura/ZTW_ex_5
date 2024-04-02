package com.example.Lab5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Book {
    private int id;
    private String title;
    private String subtitle;
    private List<Author> authors;
    private int yearOfPublication;
}
