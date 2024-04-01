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

    private boolean assertReaderHasBorrowRight(Reader reader) {
        return memoryRepository.getLendingRegister()
                .stream()
                .anyMatch(record -> record.getReader().equals(reader) &&
                        record.getBookReturnDate() == null &&
                        record.getExpectedBookReturnDate().isBefore(LocalDate.now()));
    }

    private boolean assertBookAvailable(Book book) {
        return memoryRepository.getBooksCopies()
                .stream()
                .noneMatch(it -> it.getBook().assertBooksMatched(book) && it.getCurrentAvailable() > 0);
    }

    private Optional<Reader> getReader(Reader reader) {
        return memoryRepository.getReaders()
                .stream()
                .filter(foundReader ->
                        foundReader.firstName().equals(reader.firstName()) &&
                                foundReader.lastName().equals(reader.lastName())
                ).findFirst();
    }

    private Optional<Book> getBook(Book book) {
        return memoryRepository.getBooks()
                .stream()
                .filter(foundBook -> foundBook.assertBooksMatched(book))
                .findFirst();
    }

    private void updateBooksCurrentlyAvailable(Book book) {
        memoryRepository.getBooksCopies()
                .stream()
                .filter(it -> it.getBook().assertBooksMatched(book))
                .findFirst()
                .ifPresent(bookRecord -> bookRecord.setCurrentAvailable(bookRecord.getCurrentAvailable() - 1));
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

    @Override
    public BorrowBookResult borrowBook(LendingRegisterRecord lendingRegisterRecord) {
        var readerOptional = getReader(lendingRegisterRecord.getReader());
        var bookOptional = getBook(lendingRegisterRecord.getBook());

        if (readerOptional.isEmpty())
            return BorrowBookResult.READER_NOT_FOUND;

        var reader = readerOptional.get();
        if (assertReaderHasBorrowRight(reader))
            return BorrowBookResult.READER_HAS_NO_BORROW_RIGHT;

        if (bookOptional.isEmpty())
            return BorrowBookResult.BOOK_NOT_FOUND;

        var book = bookOptional.get();
        if (assertBookAvailable(book))
            return BorrowBookResult.BOOK_UNAVAILABLE;

        updateBooksCurrentlyAvailable(book);
        addNewLendingRecord(new LendingRegisterRecord(
                getLendingRegisterId(),
                book,
                reader,
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                null));
        return BorrowBookResult.SUCCESS;
    }
}
