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
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }


    @Override
    public String writeList(List<Book> bookList) {
        StringBuilder sb = new StringBuilder();
        bookList.forEach(book -> sb.append(write(book)));
        return sb.toString();
    }

    @Transactional
    @Override
    public String writeListAll() {
        return writeList(getAll());
    }

    @Transactional
    @Override
    public String writeListByName(String name) {
        return writeList(findByName(name));
    }

    @Transactional
    @Override
    public String writeListByAuthor(String name) {
        List<Book> books = findByAuthor(name);
        return writeList(books);
    }

    @Transactional
    @Override
    public String writeListByGenre(String name) {
        return writeList(findByGenre(name));
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
        return bookRepository.findByParams(name);
    }

    @Override
    public List<Book> findByAuthor(String name) {
        Optional<Author> author = authorService.getByName(name);
        if(author.isPresent()){
            return author.get().getBooks();
        }
        return Collections.emptyList();
    }

    @Override
    public List<Book> findByGenre(String name) {
        Optional<Genre> genre = genreService.getByName(name);
        if(genre.isPresent()){
            return genre.get().getBooks();
        }
        return Collections.emptyList();
    }

}
