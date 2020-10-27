package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceDBImpl implements BookServiceDB {
    private final BookRepository bookRepository;

    @Override
    public Optional<Book> getById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public void update(String id, Book book) {
        Optional<Book> old = bookRepository.findById(id);
        if (old.isPresent()){
            Book oldBook = old.get();
            oldBook.setName(book.getName());
            oldBook.setYear(book.getYear());
            oldBook.setDescription(book.getDescription());
            bookRepository.save(oldBook);
        }
    }

    @Override
    public void insert(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findByNameContains(name);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id);
    }
}
