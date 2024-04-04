package com.example.Lab5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class Author {

    private int id;
    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object object) {
        if (object instanceof Author author) {
            if (author.firstName == null && firstName != null || author.firstName != null && firstName == null) {
                return false;
            } else if (author.lastName == null && lastName != null || author.lastName != null && lastName == null) {
                return false;
            } else if (author.lastName == null && lastName == null && author.firstName == null && firstName == null) {
                return true;
            } else if (author.lastName == null && lastName == null) {
                return author.firstName.equals(firstName);
            } else if (author.firstName == null && firstName == null) {
                return author.lastName.equals(lastName);
            }
            return author.firstName.equals(firstName) && author.lastName.equals(lastName);
        }
        return false;
    }
}
