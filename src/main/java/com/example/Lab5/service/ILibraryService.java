package com.example.Lab5.service;

import com.example.Lab5.model.*;

import java.util.List;

public interface ILibraryService {

    enum BorrowBookResult {
        BOOK_NOT_FOUND, READER_NOT_FOUND, BOOK_UNAVAILABLE, READER_HAS_NO_BORROW_RIGHT, SUCCESS
    }

    enum ReturnBookResult {
        BOOK_NOT_FOUND, READER_NOT_FOUND, BOOK_NOT_BORROWED, SUCCESS
    }

    List<LendingRegisterRecord> getLendingRegister();
    List<BooksCopiesRecords> getBooksCopiesRecords();
    List<Reader> getReaders();
    List<BookAndAvailabilityInLibraries> getFullBooksInfo();
    BorrowBookResult borrowBook(int bookID, int readerID);

    ReturnBookResult returnBook(int bookID, int readerID);

}
