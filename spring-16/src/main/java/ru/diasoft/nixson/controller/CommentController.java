package ru.diasoft.nixson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.dto.Done;
import ru.diasoft.nixson.dto.GenreDto;
import ru.diasoft.nixson.service.CommentServiceDB;

@RequiredArgsConstructor
@RestController()
public class CommentController {
    private final CommentServiceDB commentService;

    @GetMapping("/api/comment")
    public Iterable<Comment> list() {
        return commentService.getAll();
    }

    @PostMapping("/api/comment")
    public ResponseEntity<Done> create(@RequestBody GenreDto genre) {
        commentService.insert(genre.getBookId(), genre.getName());
        return ResponseEntity.status(HttpStatus.OK).body(Done.builder().success("done").build());
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<Done> delete(@PathVariable String id) {
        commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(Done.builder().success("done").build());
    }
}
