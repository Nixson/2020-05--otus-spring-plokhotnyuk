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
    private final CommentServiceDB commentServiceDB;

    @Override
    public void createComment(String bookId, String comment) {
        commentServiceDB.insert(bookId,comment);
    }

    @Override
    public void updateComment(String commentId, String comment) {
        commentServiceDB.update(commentId,comment);
    }

    @Override
    public Iterable<Comment> getAll() {
        return commentServiceDB.getAll();
    }

    @Override
    public List<Comment> getByBookId(String book) {
        return commentServiceDB.getByBookId(book);
    }

    @Override
    public void deleteById(String id) {
        commentServiceDB.delete(id);
    }

}
