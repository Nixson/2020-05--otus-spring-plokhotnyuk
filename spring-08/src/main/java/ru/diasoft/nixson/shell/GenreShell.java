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

    @ShellMethod(value = "List genres", key = {"genreList", "gl", "getGenreList"})
    public String getGenreList() {
        StringBuilder sb = new StringBuilder();
        genreService.getAll().forEach(genre -> sb.append(genre.getId() + " " + genre.getName()+"\n"));
        return sb.toString();
    }

    @ShellMethod(value = "Add genre to book", key = {"genreAdd", "gc", "addGenre"})
    public String addGenre(String bookId, String name) {
        return genreService.insert(bookId,name);
    }

    @ShellMethod(value = "Modify genre", key = {"genreModify", "gm", "modifyGenre"})
    public String modifyGenre(String id, String name) {
        return genreService.update(id,name);
    }

    @ShellMethod(value = "Remove genre", key = {"genreRemove", "gr", "removeGenre"})
    public String removeGenre(String id) {
        return genreService.deleteById(id);
    }
}
