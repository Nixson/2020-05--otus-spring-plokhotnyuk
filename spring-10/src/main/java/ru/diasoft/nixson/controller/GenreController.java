package ru.diasoft.nixson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.dto.AuthorDto;
import ru.diasoft.nixson.dto.Done;
import ru.diasoft.nixson.dto.GenreDto;
import ru.diasoft.nixson.service.GenreServiceDB;

@RequiredArgsConstructor
@RestController()
public class GenreController {
    private final GenreServiceDB genreService;

    @GetMapping("/api/genre")
    public Iterable<Genre> list() {
        return genreService.getAll();
    }

    @PostMapping("/api/genre")
    public ResponseEntity<Done> create(@RequestBody GenreDto genre) {
        genreService.insert(genre.getBookId(), genre.getName());
        return ResponseEntity.status(HttpStatus.OK).body(Done.builder().success("done").build());
    }

    @DeleteMapping("/api/genre/{id}")
    public ResponseEntity<Done> delete(@PathVariable String id) {
        genreService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(Done.builder().success("done").build());
    }
}
