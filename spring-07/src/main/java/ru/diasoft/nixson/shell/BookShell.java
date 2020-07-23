package ru.diasoft.nixson.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.nixson.service.BookService;
import ru.diasoft.nixson.util.BundleUtil;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {
    private final BookService bookService;
    private final BundleUtil bundleUtil;

    @ShellMethod(value = "Find by book name", key = {"list", "all", "l", "getAll"})
    public String getAll() {
        return bookService.writeList(bookService.getAll());
    }

    @ShellMethod(value = "Find by book name", key = {"n", "find", "findByName"})
    public String findByName(@ShellOption(defaultValue = "none") String bookName) {
        return bookService.writeList(bookService.findByName(bookName));
    }

    @ShellMethod(value = "Find by author", key = {"a", "author", "findByAuthor"})
    public String findByAuthor(@ShellOption(defaultValue = "none") String authorName) {
        return bookService.writeList(bookService.findByAuthor(authorName));
    }

    @ShellMethod(value = "Find by genre", key = {"g", "genre", "findByGenre"})
    public String findByGenre(@ShellOption(defaultValue = "none") String genreName) {
        return bookService.writeList(bookService.findByGenre(genreName));
    }

    @ShellMethod(value = "Add book", key = {"c", "i", "create", "insert"})
    public String insert(   String bookName
                          , String author
                          , String genre
                          , @ShellOption(defaultValue = "") String year
                          , @ShellOption(defaultValue = "") String description) {
        return bookService.insert(bookName,author,genre,year,description);
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    public String update(     Long id
                            , String bookName
                            , String author
                            , String genre
                            , @ShellOption(defaultValue = "") String year
                            , @ShellOption(defaultValue = "") String description) {
        return bookService.update(id,bookName,author,genre,year,description);
    }

    @ShellMethod(value = "Remove book", key = {"d", "delete", "rm", "remove"})
    public String delete(Long id) {
        bookService.delete(id);
        return bundleUtil.get("done");
    }
}
