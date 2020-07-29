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
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Optional<Book> getById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public void update(String id, String bookName, String year, String description) {
        Optional<Book> bookOptional = getById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            if (!bookName.isEmpty()) {
                book.setName(bookName);
            }
            if (!description.isEmpty()) {
                book.setDescription(description);
            }
            bookRepository.save(book);
        }
    }

    @Override
    public void insert(String bookName, String author, String genre, String year, String description) {
        Book book = Book
                .builder()
                .name(bookName)
                .year(year)
                .description(description)
                .build();
        bookRepository.save(book);
        authorService.insert(book.getId(),author);
        genreService.insert(book.getId(),genre);
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findByNameContains(name);
    }

    @Override
    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id);
    }
}
