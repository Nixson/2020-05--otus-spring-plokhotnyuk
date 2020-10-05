package ru.diasoft.nixson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.service.BookServiceDB;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class BookController {
    private final BookServiceDB bookService;

    @GetMapping(value = "/api/book")
    public ResponseEntity<Iterable<Book>> getBooks(){
        return ResponseEntity.ok(bookService.getAll());
    }

    @PostMapping(value = "/api/book")
    public ResponseEntity<Book> post(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.insert(book));
    }

    @PutMapping(value = "/api/book/{id}")
    public ResponseEntity<Book> put(@PathVariable Long id, @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.update(id,book));
    }

    @DeleteMapping(value = "/api/book/{id}")
    public ResponseEntity<Void> rm(@PathVariable Long id){
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }


}
