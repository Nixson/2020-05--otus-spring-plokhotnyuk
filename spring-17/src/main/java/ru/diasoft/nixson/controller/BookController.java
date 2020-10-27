package ru.diasoft.nixson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.dto.Done;
import ru.diasoft.nixson.service.BookServiceDB;

@RequiredArgsConstructor
@RestController()
public class BookController {
    private final BookServiceDB bookService;

    @GetMapping("/api/book")
    public Iterable<Book> list() {
        return bookService.getAll();
    }

    @GetMapping("/api/book/{id}")
    public Book info(@PathVariable String id){
        return bookService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/api/book/{id}")
    public Book update(@PathVariable String id, @RequestBody Book book){
        bookService.update(id,book);
        return bookService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/book")
    public ResponseEntity<Book> save(@RequestBody Book book){
        bookService.insert(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @DeleteMapping("/api/book/{id}")
    public ResponseEntity<Done> delete(@PathVariable String id){
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(Done.builder().success("done").build());
    }
}
