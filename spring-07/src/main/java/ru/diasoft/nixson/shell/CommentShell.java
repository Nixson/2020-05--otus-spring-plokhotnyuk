package ru.diasoft.nixson.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.service.CommentService;

import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class CommentShell {
    private final CommentService commentService;

    @ShellMethod(value = "Comment list", key = {"commentList", "comments", "cm"})
    public String list(@ShellOption(defaultValue = "0") Long book) {
        Iterable<Comment> cl;
        if (book > 0){
            cl = commentService.getByBookId(book);
        } else {
            cl = commentService.getAll();
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

    @ShellMethod(value = "Add comment", key = {"addComment", "ac"})
    public void add(Long bookId, String comment) {
        commentService.createComment(bookId, comment);
    }

    @ShellMethod(value = "Update comment", key = {"updateComment", "uc"})
    public void updateGenre(long id, String comment) {
        commentService.updateComment(id, comment);
    }

    @ShellMethod(value = "Delete comment", key = {"deleteComment", "dc"})
    public void delete(long id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "Delete all comments", key = {"delete-all-comments", "dac"})
    public void deleteAll() {
        commentService.deleteAll();
    }
}
