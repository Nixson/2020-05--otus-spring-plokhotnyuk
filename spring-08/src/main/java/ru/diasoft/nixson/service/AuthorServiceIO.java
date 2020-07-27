package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceIO implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Iterable<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public String insert(String bookId, String name) {
        authorRepository.add(bookId,Author.builder().name(name).build());
        return "done";
    }

    @Override
    public String update(String id, String name) {
        authorRepository.update(id,Author.builder().name(name).build());
        return "done";
    }

    @Override
    public String deleteById(String id) {
        authorRepository.deleteById(id);
        return "done";
    }
}
