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

    @Transactional
    @Override
    public String listByParam(Long id) {
        List<Comment> cl;
        if (id > 0){
            Optional<Book> book = bookRepository.findById(id);
            if(book.isPresent()) {
                cl = book.get().getComments();
            } else {
                cl = Collections.emptyList();
            }
        } else {
            cl = commentRepository.findAll();
        }
        StringBuilder sb = new StringBuilder();
        cl.forEach(comment -> sb.append(comment.getId())
                .append(" [")
                .append(comment.getBook().getId())
                .append("] ")
                .append(comment.getContent())
                .append("\n"));
        return sb.toString();
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
