package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.math.NumberUtils;
import ru.diasoft.nixson.dao.BookDao;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.util.IOUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceIO implements BookService {
    private final IOUtil ioUtil;
    private final BookDao bookDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return bookDao.getById(id);
    }

    @Override
    public void writeAll() {
        getAll().forEach(this::write);
    }

    @Override
    public void writeList(List<Book> bookList) {
        bookList.forEach(this::write);
    }

    @Override
    public void write(Book book) {
        ioUtil.writeLn("book-name-info",book.getId(),book.getName());
        ioUtil.writeLn("book-year-info",book.getYear());
        ioUtil.writeLn("book-author-info",book.getAuthor().getName());
        ioUtil.writeLn("book-genre-info",book.getGenre().getName());
        ioUtil.writeLn("book-description-info",book.getDescription());
        ioUtil.writeText("");
    }

    @Override
    public void writeById(Long id) {
        write(getById(id).get());
    }

    @Override
    public void delete(Long id) {
        bookDao.deleteById(id);
    }

    @Override
    public void update(Long id) {
        Optional<Book> bookOptional = getById(id);
        if(bookOptional.isEmpty()) {
            return;
        }
        Book book = bookOptional.get();
        ioUtil.write("book-name");
        String name = ioUtil.read();
        if (!name.isEmpty()) {
            book.setName(name);
        }
        ioUtil.write("author-list");
        authorService.getAll().forEach(author -> ioUtil.writeText(author.getId() + ") " + author.getName()));
        String authorStr = ioUtil.read();
        Author author = null;
        if (NumberUtils.isDigits(authorStr)) {
            book.setAuthor(authorService.getById(Long.valueOf(authorStr)).get());
        } else {
            if(!authorStr.isEmpty()){
                book.setAuthor(authorService.insert(Author.builder().name(authorStr).build()));
            }
        }
        Genre genre = getGenre(false);
        if (genre != null) {
            book.setGenre(genre);
        }
        ioUtil.write("book-year");
        String year = ioUtil.read();
        if(!year.isEmpty()) {
            book.setYear(year);
        }
        ioUtil.write("book-description");
        String description = ioUtil.read();
        if(!description.isEmpty()) {
            book.setDescription(description);
        }
        bookDao.update(book);
        ioUtil.write("done");
    }

    @Override
    public void insert() {
        Book book = new Book();
        ioUtil.write("book-name");
        book.setName(ioUtil.read());
        ioUtil.write("author-list");
        authorService.getAll().forEach(author -> ioUtil.writeText(author.getId() + ") " + author.getName()));
        String authorStr = ioUtil.read();
        Author author = null;
        if (NumberUtils.isDigits(authorStr)) {
            author = authorService.getById(Long.valueOf(authorStr)).get();
        } else {
            author = authorService.insert(Author.builder().name(authorStr).build());
        }
        book.setAuthor(author);
        book.setGenre(getGenre(true));
        ioUtil.write("book-year");
        book.setYear(ioUtil.read());
        ioUtil.write("book-description");
        book.setDescription(ioUtil.read());
        book.setId(bookDao.getLastId()+1);
        bookDao.insert(book);
        ioUtil.write("done");
    }

    @Override
    public List<Book> findByName(String name) {
        return bookDao.findByName(name);
    }

    @Override
    public List<Book> findByAuthor(String name) {
        return bookDao.findByAuthor(name);
    }

    @Override
    public List<Book> findByGenre(String name) {
        return bookDao.findByGenre(name);
    }

    private Genre getGenre(boolean isReq){
        ioUtil.write("genre-list");
        genreService.getAll().forEach(genre -> ioUtil.writeText(genre.getId() + ") " + genre.getName()));
        String genreString = ioUtil.read();
        Optional<Genre> genre = null;
        if (NumberUtils.isDigits(genreString)) {
            genre = genreService.getById(Long.valueOf(genreString));
        } else {
            genre = Optional.of(genreService.insert(Genre.builder().name(genreString).build()));
        }
        if(genre.isEmpty() && isReq) {
            ioUtil.writeLn("error-data");
            return getGenre(isReq);
        }
        return genre.get();
    }
}
