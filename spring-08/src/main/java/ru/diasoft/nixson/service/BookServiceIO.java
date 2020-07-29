package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.util.BundleUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceIO implements BookService {
    private final BundleUtil bundleUtil;
    private final BookServiceDB bookServiceDB;

    @Override
    public Iterable<Book> getAll() {
        return bookServiceDB.getAll();
    }

    @Override
    public Optional<Book> getById(String id) {
        return bookServiceDB.getById(id);
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
        bookServiceDB.delete(id);
    }

    @Override
    public String update(String id
            , String bookName
            , String year
            , String description) {
        Optional<Book> bookOptional = bookServiceDB.getById(id);
        if (bookOptional.isEmpty()) {
            return bundleUtil.get("error-data");
        }
        bookServiceDB.update(id,bookName,year,description);
        return bundleUtil.get("done");
    }

    @Override
    public String insert(String bookName, String author, String genre, String year, String description) {
        bookServiceDB.insert(bookName,author,genre,year,description);
        return bundleUtil.get("done");
    }

    @Override
    public List<Book> findByName(String name) {
        return bookServiceDB.findByName(name);
    }

}
