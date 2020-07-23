package ru.diasoft.nixson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
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
    private final BundleUtil bundleUtil;

    @Override
    public void createComment(long bookId, String comment) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException(bundleUtil.get("book-not-found")));
        commentRepository.save(Comment.builder()
                .content(comment)
                .book(book)
                .build());
    }

    @Override
    public void updateComment(long commentId, String comment) {
        Comment commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException(bundleUtil.get("book-not-found")));
        commentEntity.setContent(comment);
        commentRepository.save(commentEntity);
    }

    @Override
    public Iterable<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getByBookId(Long book) {
        Optional<Book> book1 = bookRepository.findById(book);
        if(book1.isPresent()) {
            return book1.get().getComments();
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        commentRepository.deleteAll();
    }
}
