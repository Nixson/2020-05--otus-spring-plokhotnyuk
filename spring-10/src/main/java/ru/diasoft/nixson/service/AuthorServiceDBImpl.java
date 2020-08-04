package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceDBImpl implements AuthorServiceDB {
    private final AuthorRepository authorRepository;

    @Override
    public Iterable<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public void insert(String parentId, String name) {
        authorRepository.add(parentId,Author.builder().name(name).build());
    }

    @Override
    public void update(String id, String name) {
        authorRepository.update(id,Author.builder().name(name).build());
    }

    @Override
    public void delete(String id) {
        authorRepository.deleteById(id);
    }
}
