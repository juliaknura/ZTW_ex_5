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

    public void setAttributes(Author author) {
        firstName = author.getFirstName();
        lastName = author.getLastName();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Author author) {
            return author.firstName.equals(firstName) && author.lastName.equals(lastName);
        }
        return false;
    }
}
