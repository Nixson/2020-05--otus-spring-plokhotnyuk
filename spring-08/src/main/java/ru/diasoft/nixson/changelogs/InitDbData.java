package ru.diasoft.nixson.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.diasoft.nixson.domain.Author;
import ru.diasoft.nixson.domain.Book;
import ru.diasoft.nixson.domain.Comment;
import ru.diasoft.nixson.domain.Genre;
import ru.diasoft.nixson.repository.BookRepository;

import java.util.List;
import java.util.UUID;

@ChangeLog(order = "001")
public class InitDbData {
    @ChangeSet(order = "001", id = "dropDB", runAlways = true, author = "nixson")
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "initBooks", runAlways = true, author = "nixson")
    public void initBooks(BookRepository repository) {
        repository.save(Book.builder()
                .name("Chronicles of Amber")
                .year("1970")
                .description("The Chronicles of Amber is a series of fantasy novels by American writer Roger Zelazny. The main series consists of two story arcs, each five novels in length. Additionally, there are a number of Amber short stories and other works. Four additional prequel books, authorized by the Zelazny estate following his death, were authored by John Gregory Betancourt.")
                .authors(List.of(
                        Author.builder()
                                .name("Volkov")
                                .id(UUID.randomUUID().toString())
                                .build(),
                        Author.builder()
                                .id(UUID.randomUUID().toString())
                                .name("Zelazny")
                                .build()))
                .genres(List.of(
                        Genre.builder()
                                .id(UUID.randomUUID().toString())
                                .name("fantasy")
                                .build(),
                        Genre.builder()
                                .id(UUID.randomUUID().toString())
                                .name("novel")
                                .build()
                ))
                .comments(List.of(
                        Comment.builder()
                                .id(UUID.randomUUID().toString())
                                .content("The Amber stories take place in two contrasting true worlds, Amber and Chaos, and in shadow worlds (Shadows) that lie between the two")
                                .build(),
                        Comment.builder()
                                .id(UUID.randomUUID().toString())
                                .content("The series of books was published over the years from 1970 to 1991")
                                .build()))
                .build());
    }
}
