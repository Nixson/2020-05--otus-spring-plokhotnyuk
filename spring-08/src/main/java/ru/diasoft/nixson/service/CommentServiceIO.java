package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.BookRepository;
import ru.diasoft.nixson.repository.CommentRepository;
import ru.diasoft.nixson.util.BundleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceIO implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    public void createComment(String bookId, String comment) {
        commentRepository.add(bookId, Comment.builder().content(comment).build());
    }

    @Override
    public void updateComment(String commentId, String comment) {
        commentRepository.update(commentId, Comment.builder().content(comment).build());
    }

    @Override
    public Iterable<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getByBookId(String book) {
        Optional<Book> book1 = bookRepository.findById(book);
        if(book1.isPresent()) {
            return book1.get().getComments();
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }

}
