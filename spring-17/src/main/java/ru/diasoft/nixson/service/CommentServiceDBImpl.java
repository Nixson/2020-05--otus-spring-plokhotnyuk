package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.repository.BookRepository;
import ru.diasoft.nixson.repository.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceDBImpl implements CommentServiceDB {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public Iterable<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public void insert(String parentId, String name) {
        commentRepository.add(parentId, Comment.builder().content(name).build());
    }

    @Override
    public void update(String id, String name) {
        commentRepository.update(id, Comment.builder().content(name).build());
    }

    @Override
    public void delete(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getByBookId(String bookId) {
        Optional<Book> book1 = bookRepository.findById(bookId);
        if(book1.isPresent()) {
            return book1.get().getComments();
        }
        return Collections.emptyList();
    }
}
