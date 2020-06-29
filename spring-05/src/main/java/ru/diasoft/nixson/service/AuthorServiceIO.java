package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.dao.AuthorDao;
import ru.diasoft.nixson.domain.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceIO implements AuthorService {
    private final AuthorDao authorDao;
    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Optional<Author> getById(Long id) {
        return authorDao.getById(id);
    }

    @Override
    public Author insert(Author author) {
        if(author.getId()==null) {
            author.setId(authorDao.getLastId()+1);
        }
        authorDao.insert(author);
        return author;
    }
}
