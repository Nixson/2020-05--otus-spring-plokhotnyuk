package ru.diasoft.nixson.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.diasoft.nixson.repository.BookRepository;

@ChangeLog(order = "001")
public class InitDbData {
    @ChangeSet(order = "001", id = "dropDB", runAlways = true, author = "nixson")
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "002", id = "initBooks", runAlways = true, author = "nixson")
    public void initBooks(BookRepository repository) {
    }
}
