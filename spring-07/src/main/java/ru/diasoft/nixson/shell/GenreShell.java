package ru.diasoft.nixson.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.diasoft.nixson.service.AuthorService;
import ru.diasoft.nixson.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class GenreShell {
    private final GenreService genreService;

    @ShellMethod(value = "List authors", key = {"genreList", "gl", "getGenreList"})
    public String getGenreList() {
        StringBuilder sb = new StringBuilder();
        genreService.getAll().forEach(genre -> sb.append(genre.getId() + " " + genre.getName()+"\n"));
        return sb.toString();
    }
}
