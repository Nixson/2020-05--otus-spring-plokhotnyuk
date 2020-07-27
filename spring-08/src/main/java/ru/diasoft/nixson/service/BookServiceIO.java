package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.BookRepository;
import ru.diasoft.nixson.util.BundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceIO implements BookService {
    private final BundleUtil bundleUtil;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getById(String id) {
        return bookRepository.findById(id);
    }


    @Override
    public String writeList(Iterable<Book> bookList) {
        StringBuilder sb = new StringBuilder();
        bookList.forEach(book -> sb.append(write(book)));
        return sb.toString();
    }

    @Override
    public String write(Book book) {
        return bundleUtil.get("book-name-info", book.getId(), book.getName()) + "\n" +
                bundleUtil.get("book-year-info", book.getYear()) + "\n" +
                bundleUtil.get("book-author-info", toStringAuthors(book.getAuthors())) + "\n" +
                bundleUtil.get("book-genre-info", toStringGenres(book.getGenres())) + "\n" +
                bundleUtil.get("book-description-info", book.getDescription()) + "\n";
    }

    private String toStringAuthors(List<Author> authorList){
        StringBuilder sb = new StringBuilder();
        authorList.forEach(author -> sb.append(author.getName()+";"));
        return sb.toString();
    };

    private String toStringGenres(List<Genre> genreList){
        StringBuilder sb = new StringBuilder();
        genreList.forEach(genre -> sb.append(genre.getName()+";"));
        return sb.toString();
    };

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    public String update(String id
            , String bookName
            , String year
            , String description) {
        Optional<Book> bookOptional = getById(id);
        if (bookOptional.isEmpty()) {
            return bundleUtil.get("error-data");
        }
        Book book = bookOptional.get();
        if (!bookName.isEmpty()) {
            book.setName(bookName);
        }
        if (!description.isEmpty()) {
            book.setDescription(description);
        }
        bookRepository.save(book);
        return bundleUtil.get("done");
    }

    @Override
    public String insert(String bookName, String author, String genre, String year, String description) {
        Book book = Book
                .builder()
                .name(bookName)
                .year(year)
                .description(description)
                .build();
        bookRepository.save(book);
        authorService.insert(book.getId(),author);
        genreService.insert(book.getId(),genre);
        return bundleUtil.get("done");
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findByNameContains(name);
    }

}
