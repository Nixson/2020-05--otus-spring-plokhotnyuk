package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.BookRepository;
import ru.diasoft.nixson.util.BundleUtil;

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
    public Optional<Book> getById(Long id) {
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
                bundleUtil.get("book-author-info", book.getAuthor().getName()) + "\n" +
                bundleUtil.get("book-genre-info", book.getGenre().getName()) + "\n" +
                bundleUtil.get("book-description-info", book.getDescription()) + "\n";
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public String update(Long id
            , String bookName
            , String author
            , String genre
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
        Optional<Author> optionalAuthor = authorService.getByParam(author);
        if (optionalAuthor.isEmpty())
            return bundleUtil.get("error-data");
        book.setAuthor(optionalAuthor.get());
        Optional<Genre> optionalGenre = genreService.getByParam(genre);
        if (optionalGenre.isEmpty())
            return bundleUtil.get("error-data");
        book.setGenre(optionalGenre.get());
        if (!year.isEmpty()) {
            book.setYear(year);
        }
        if (!description.isEmpty()) {
            book.setDescription(description);
        }
        bookRepository.save(book);
        return bundleUtil.get("done");
    }

    @Transactional
    @Override
    public String insert(String bookName, String author, String genre, String year, String description) {
        Book book = Book
                .builder()
                .name(bookName)
                .year(year)
                .description(description)
                .build();
        Optional<Author> optionalAuthor = authorService.getByParam(author);
        if (optionalAuthor.isEmpty())
            return bundleUtil.get("error-data");
        book.setAuthor(optionalAuthor.get());
        Optional<Genre> optionalGenre = genreService.getByParam(genre);
        if (optionalGenre.isEmpty())
            return bundleUtil.get("error-data");
        book.setGenre(optionalGenre.get());
        bookRepository.save(book);
        return bundleUtil.get("done");
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findByName(name);
    }

    @Override
    public List<Book> findByAuthor(String name) {
        return bookRepository.findByAuthorName(name);
    }

    @Override
    public List<Book> findByGenre(String name) {
        return bookRepository.findByGenreName(name);
    }

}
