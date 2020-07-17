package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceIO implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    public Optional<Author> getById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> getByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Author insert(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Optional<Author> getByParam(String authorNameOrId) {
        if (NumberUtils.isDigits(authorNameOrId))
            return getById(Long.valueOf(authorNameOrId));
        return Optional.of(authorRepository.save(Author.builder().name(authorNameOrId).build()));
    }
}
