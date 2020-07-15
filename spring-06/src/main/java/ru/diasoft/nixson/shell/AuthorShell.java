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

}
