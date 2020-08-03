package ru.diasoft.nixson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.dto.AuthorDto;
import ru.diasoft.nixson.dto.Done;
import ru.diasoft.nixson.service.AuthorServiceDB;

@RequiredArgsConstructor
@RestController()
public class AuthorController {
    private final AuthorServiceDB authorService;


    @GetMapping("/api/author")
    public Iterable<Author> list() {
        return authorService.getAll();
    }

    @PostMapping("/api/author")
    public ResponseEntity<Done> create(@RequestBody AuthorDto author) {
        authorService.insert(author.getBookId(), author.getName());
        return ResponseEntity.status(HttpStatus.OK).body(Done.builder().success("done").build());
    }

    @DeleteMapping("/api/author/{id}")
    public ResponseEntity<Done> delete(@PathVariable String id) {
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(Done.builder().success("done").build());
    }
}
