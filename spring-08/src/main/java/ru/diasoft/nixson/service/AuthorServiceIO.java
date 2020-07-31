package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.domain.Author;

@Service
@RequiredArgsConstructor
public class AuthorServiceIO implements AuthorService {
    private final AuthorServiceDB authorServiceDB;

    @Override
    public Iterable<Author> getAll() {
        return authorServiceDB.getAll();
    }

    @Override
    public String insert(String bookId, String name) {
        authorServiceDB.insert(bookId,name);
        return "done";
    }

    @Override
    public String update(String id, String name) {
        authorServiceDB.update(id,name);
        return "done";
    }

    @Override
    public String deleteById(String id) {
        authorServiceDB.delete(id);
        return "done";
    }
}
