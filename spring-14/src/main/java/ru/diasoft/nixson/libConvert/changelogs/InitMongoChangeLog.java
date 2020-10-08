package ru.diasoft.nixson.libConvert.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.diasoft.nixson.libConvert.domain.Author;
import ru.diasoft.nixson.libConvert.domain.Book;
import ru.diasoft.nixson.libConvert.domain.Genre;

import java.util.List;
import java.util.UUID;

@ChangeLog(order = "001")
public class InitMongoChangeLog {

    @ChangeSet(order = "001", id = "dropDB", author = "nixson", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "initBooks", author = "nixson", runAlways = true)
    public void createBooks(MongockTemplate template) {
        template.save(Book.builder()
                .name("Nine princes in Amber")
                .year("1970")
                .description("Престол таинственного Янтарного королевства - приз победителю...")
                .authors(List.of(
                        Author.builder()
                                .name("Zelazny Roger")
                                .id(UUID.randomUUID().toString())
                                .build(),
                        Author.builder()
                                .id(UUID.randomUUID().toString())
                                .name("Saukov")
                                .build()))
                .genres(List.of(
                        Genre.builder()
                                .id(UUID.randomUUID().toString())
                                .name("Novel")
                                .build(),
                        Genre.builder()
                                .id(UUID.randomUUID().toString())
                                .name("Fantasy")
                                .build()
                ))
                .build());

        template.save(Book.builder()
                .name("The warrior`s apperentice")
                .year("1986")
                .description("Это Вселенная могущественных супердержав и долгих жестоких войн")
                .authors(List.of(
                        Author.builder()
                                .name("Lois McMaster")
                                .id(UUID.randomUUID().toString())
                                .build(),
                        Author.builder()
                                .id(UUID.randomUUID().toString())
                                .name("Bujold")
                                .build()))
                .genres(List.of(
                        Genre.builder()
                                .id(UUID.randomUUID().toString())
                                .name("Fantasy")
                                .build(),
                        Genre.builder()
                                .id(UUID.randomUUID().toString())
                                .name("Novel")
                                .build(),
                        Genre.builder()
                                .id(UUID.randomUUID().toString())
                                .name("Science fiction")
                                .build()
                ))
                .build());
    }
}
