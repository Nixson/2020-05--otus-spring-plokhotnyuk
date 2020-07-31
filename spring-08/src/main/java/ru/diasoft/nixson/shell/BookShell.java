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

    @ShellMethod(value = "View all books", key = {"list", "all", "l", "getAll"})
    public String getAll() {
        return bookService.writeList(bookService.getAll());
    }

    @ShellMethod(value = "Find by book name", key = {"n", "find", "findByName"})
    public String findByName(@ShellOption(defaultValue = "none") String bookName) {
        return bookService.writeList(bookService.findByName(bookName));
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
    public String update(     String id
                            , String bookName
                            , @ShellOption(defaultValue = "") String year
                            , @ShellOption(defaultValue = "") String description) {
        return bookService.update(id,bookName,year,description);
    }

    @ShellMethod(value = "Remove book", key = {"d", "delete", "rm", "remove"})
    public String delete(String id) {
        bookService.delete(id);
        return bundleUtil.get("done");
    }
}
