package ru.diasoft.nixson.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.service.CommentService;

@RequiredArgsConstructor
@ShellComponent
public class CommentShell {
    private final CommentService commentService;

    @ShellMethod(value = "Comment list", key = {"commentList", "comments", "cm"})
    public String list(@ShellOption(defaultValue = "") String book) {
        Iterable<Comment> cl;
        if (!book.isEmpty()) {
            cl = commentService.getByBookId(book);
        } else {
            cl = commentService.getAll();
        }
        StringBuilder sb = new StringBuilder();
        cl.forEach(comment -> sb.append(comment.getId())
                .append(comment.getContent())
                .append("\n"));
        return sb.toString();
    }

    @ShellMethod(value = "Add comment", key = {"addComment", "ac"})
    public void add(String bookId, String comment) {
        commentService.createComment(bookId, comment);
    }

    @ShellMethod(value = "Update comment", key = {"updateComment", "uc"})
    public void updateGenre(String id, String comment) {
        commentService.updateComment(id, comment);
    }

    @ShellMethod(value = "Delete comment", key = {"deleteComment", "dc"})
    public void delete(String id) {
        commentService.deleteById(id);
    }

}
