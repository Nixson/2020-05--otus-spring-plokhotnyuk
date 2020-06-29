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

    @ShellMethod(value = "Find by book name", key = {"n", "find", "findByName"})
    public String findByName(@ShellOption(defaultValue = "none") String bookName) {
        bookService.writeList(bookService.findByName(bookName));
        return "";
    }

    @ShellMethod(value = "Find by author", key = {"a", "author", "findByAuthor"})
    public String findByAuthor(@ShellOption(defaultValue = "none") String authorName) {
        bookService.writeList(bookService.findByAuthor(authorName));
        return "";
    }

    @ShellMethod(value = "Find by genre", key = {"g", "genre", "findByGenre"})
    public String findByGenre(@ShellOption(defaultValue = "none") String genreName) {
        bookService.writeList(bookService.findByGenre(genreName));
        return "";
    }

    @ShellMethod(value = "Add book", key = {"c", "i", "create", "insert"})
    public String insert() {
        bookService.insert();
        return "";
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    public String update(Long id) {
        bookService.update(id);
        return "";
    }

    @ShellMethod(value = "Remove book", key = {"d", "delete","rm","remove"})
    public String delete(Long id) {
        bookService.delete(id);
        return bundleUtil.get("done");
    }
}
