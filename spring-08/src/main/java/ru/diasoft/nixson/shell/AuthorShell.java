package ru.diasoft.nixson.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.diasoft.nixson.service.AuthorService;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShell {
    private final AuthorService authorService;

    @ShellMethod(value = "List authors", key = {"authorList", "al", "getAuthorList"})
    public String getAuthorList() {
        StringBuilder sb = new StringBuilder();
        authorService.getAll().forEach(author -> sb.append(author.getId() + " " + author.getName()+"\n"));
        return sb.toString();
    }

    @ShellMethod(value = "Add author to book", key = {"authorAdd", "ac", "addAuthor"})
    public String addAuthor(String bookId, String name) {
        return authorService.insert(bookId,name);
    }

    @ShellMethod(value = "Modify author", key = {"authorModify", "am", "modifyAuthor"})
    public String modifyAuthor(String id, String name) {
        return authorService.update(id,name);
    }

    @ShellMethod(value = "Remove author", key = {"authorRemove", "ar", "removeAuthor"})
    public String removeAuthor(String id) {
        return authorService.deleteById(id);
    }
}
