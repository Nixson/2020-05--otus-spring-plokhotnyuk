package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.repository.BookRepository;
import ru.diasoft.nixson.repository.CommentRepository;
import ru.diasoft.nixson.util.BundleUtil;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceIO implements CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final BundleUtil bundleUtil;

    @Transactional
    @Override
    public void createComment(long bookId, String comment) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException(bundleUtil.get("book-not-found")));
        commentRepository.save(Comment.builder()
                .content(comment)
                .book(book)
                .build());
    }

    @Transactional
    @Override
    public void updateComment(long commentId, String comment) {
        Comment commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException(bundleUtil.get("book-not-found")));
        commentEntity.setContent(comment);
        commentRepository.save(commentEntity);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getByBookId(Long book) {
        return commentRepository.findByBookId(book);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }
}
