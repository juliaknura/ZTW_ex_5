package com.example.Lab5.service;

import com.example.Lab5.model.*;
import com.example.Lab5.repository.IMemoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public final class LibraryService implements ILibraryService {

    private final IMemoryRepository memoryRepository;

    @Override
    public List<LendingRegisterRecord> getLendingRegister() {
        return memoryRepository.getLendingRegister();
    }

    @Override
    public List<BooksCopiesRecords> getBooksCopiesRecords() {
        return memoryRepository.getBooksCopies();
    }

    @Override
    public List<Reader> getReaders() {
        return memoryRepository.getReaders();
    }

    @Override
    public List<BookAndAvailabilityInLibraries> getFullBooksInfo() {
        return memoryRepository.getFullBooksInfo();
    }

    private boolean assertReaderHasBorrowRight(int readerID) {
        return memoryRepository.getLendingRegister()
                .stream()
                .anyMatch(record -> record.getReaderID() == readerID &&
                        record.getBookReturnDate() == null &&
                        record.getExpectedBookReturnDate().isBefore(LocalDate.now()));
    }

    private boolean assertBookAvailable(int bookID) {
        return memoryRepository.getBooksCopies()
                .stream()
                .noneMatch(it -> it.getBookID()==bookID && it.getCurrentAvailable() > 0);
    }

    private Optional<Reader> getReader(int readerID) {
        return memoryRepository.getReaders()
                .stream()
                .filter(foundReader -> foundReader.id() == readerID).findFirst();
    }

    private Optional<Book> getBook(int bookID) {
        return memoryRepository.getBooks()
                .stream()
                .filter(foundBook -> foundBook.getId()==bookID)
                .findFirst();
    }

    private void updateBooksCurrentlyAvailable(int bookID, int qty) {
        memoryRepository.getBooksCopies()
                .stream()
                .filter(it -> it.getBookID() == bookID)
                .findFirst()
                .ifPresent(bookRecord -> bookRecord.setCurrentAvailable(bookRecord.getCurrentAvailable() + qty));
    }

    private void addNewLendingRecord(LendingRegisterRecord lendingRegisterRecord) {
        memoryRepository.getLendingRegister().add(lendingRegisterRecord);
    }

    private int getLendingRegisterId() {
        return memoryRepository.getLendingRegister()
                .stream()
                .max(Comparator.comparingInt(LendingRegisterRecord::getId))
                .map(LendingRegisterRecord::getId)
                .orElse(0) + 1;
    }

    private boolean assertBookBorrowed(int bookID, int readerID) {
        return memoryRepository.getLendingRegister().stream()
                .noneMatch(it -> it.getBookID() == bookID && it.getReaderID() == readerID && it.getBookReturnDate()==null);
    }

    private void updateLendingRecord(int bookID, int readerID) {
        memoryRepository.getLendingRegister().stream()
                .filter(it -> it.getBookID() == bookID && it.getReaderID() == readerID && it.getBookReturnDate()==null)
                .findFirst()
                .ifPresent(it -> it.setBookReturnDate(LocalDate.now()));
    }

    @Override
    public BorrowBookResult borrowBook(int bookID, int readerID) {
        var readerOptional = getReader(readerID);

        if (readerOptional.isEmpty())
            return BorrowBookResult.READER_NOT_FOUND;

        var reader = readerOptional.get();
        if (assertReaderHasBorrowRight(reader.id()))
            return BorrowBookResult.READER_HAS_NO_BORROW_RIGHT;

        var bookOptional = getBook(bookID);
        if (bookOptional.isEmpty())
            return BorrowBookResult.BOOK_NOT_FOUND;

        var book = bookOptional.get();
        if (assertBookAvailable(book.getId()))
            return BorrowBookResult.BOOK_UNAVAILABLE;

        updateBooksCurrentlyAvailable(book.getId(), -1);
        addNewLendingRecord(new LendingRegisterRecord(
                getLendingRegisterId(),
                book.getId(),
                reader.id(),
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                null));
        return BorrowBookResult.SUCCESS;
    }

    @Override
    public ReturnBookResult returnBook(int bookID, int readerID) {
        var readerOptional = getReader(readerID);

        if (readerOptional.isEmpty())
            return ReturnBookResult.READER_NOT_FOUND;

        var bookOptional = getBook(bookID);
        if (bookOptional.isEmpty())
            return ReturnBookResult.BOOK_NOT_FOUND;

        var book = bookOptional.get();
        if(assertBookBorrowed(book.getId(), readerOptional.get().id()))
            return ReturnBookResult.BOOK_NOT_BORROWED;

        updateBooksCurrentlyAvailable(book.getId(), 1);
        updateLendingRecord(book.getId(), readerOptional.get().id());

        return ReturnBookResult.SUCCESS;
    }
}
