package ru.diasoft.nixson.libConvert.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.diasoft.nixson.libConvert.domain.Author;
import ru.diasoft.nixson.libConvert.domain.Book;
import ru.diasoft.nixson.libConvert.domain.Genre;
import ru.diasoft.nixson.libConvert.reader.AggregateMongoReader;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfig {
    public static final String CONVERT_BOOK_JOB = "convertBook";
    private static final int CHUNK_SIZE = 50;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job convertBook(@Qualifier("createTempTables") Step createTempTables,
                              @Qualifier("importTmpBook") Step importTmpBook,
                              @Qualifier("importTmpGenre") Step importTmpGenre,
                              @Qualifier("importTmpAuthor") Step importTmpAuthor,
                              @Qualifier("importBook") Step importBook,
                              @Qualifier("importAuthor") Step importAuthor,
                              @Qualifier("importBookAuthor") Step importBookAuthor,
                              @Qualifier("importGenre") Step importGenre,
                              @Qualifier("importBookGenre") Step importBookGenre,
                              @Qualifier("dropTempTables") Step dropTempTables) {
        return jobBuilderFactory.get(CONVERT_BOOK_JOB)
                .incrementer(new RunIdIncrementer())
                .start(createTempTables)
                .next(importTmpAuthor)
                .next(importTmpGenre)
                .next(importTmpBook)
                .next(importBook)
                .next(importAuthor)
                .next(importBookAuthor)
                .next(importGenre)
                .next(importBookGenre)
                .next(dropTempTables)
                .build();
    }

    @Bean
    public Step importTmpAuthor(AggregateMongoReader<Author> authorAggregateMongoReader,
                                 @Qualifier("tmpAuthorWriter") ItemWriter<Author> tmpAuthorWriter) {
        return stepBuilderFactory.get("importTmpAuthor")
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(authorAggregateMongoReader)
                .writer(tmpAuthorWriter)
                .build();
    }

    @Bean
    public Step importTmpGenre(AggregateMongoReader<Genre> genreReader,
                                @Qualifier("tmpGenreWriter") ItemWriter<Genre> tmpGenreWriter) {
        return stepBuilderFactory.get("importTmpGenre")
                .<Genre, Genre>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .writer(tmpGenreWriter)
                .build();
    }

    @Bean
    public Step importTmpBook(@Qualifier("mongoBookReader") MongoItemReader<Book> mongoBookReader,
                               @Qualifier("tmpBookWriter") ItemWriter<Book> bookTempWriter) {
        return stepBuilderFactory.get("importTmpBook")
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(mongoBookReader)
                .writer(bookTempWriter)
                .build();
    }

    @Bean
    public Step importBook(@Qualifier("tmpBookReader") ItemReader<Book> tmpBookReader,
                            @Qualifier("bookWriter") ItemWriter<Book> bookWriter) {
        return stepBuilderFactory.get("importBook")
                .<Book, Book>chunk(CHUNK_SIZE)
                .reader(tmpBookReader)
                .writer(bookWriter)
                .build();
    }

    @Bean
    public Step importAuthor(@Qualifier("tmpAuthorReader") ItemReader<Author> tmpAuthorReader,
                              @Qualifier("authorWriter") ItemWriter<Author> authorWriter) {
        return stepBuilderFactory.get("importTmpAuthor")
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(tmpAuthorReader)
                .writer(authorWriter)
                .build();
    }

    @Bean
    public Step importBookAuthor(@Qualifier("tmpAuthorReader") ItemReader<Author> tmpAuthorReader,
                                  @Qualifier("bookAuthorWriter") ItemWriter<Author> bookAuthorWriter) {
        return stepBuilderFactory.get("importBookAuthor")
                .<Author, Author>chunk(CHUNK_SIZE)
                .reader(tmpAuthorReader)
                .writer(bookAuthorWriter)
                .build();
    }

    @Bean
    public Step importGenre(@Qualifier("tmpGenreReader") ItemReader<Genre> tmpGenreReader,
                             @Qualifier("genreWriter") ItemWriter<Genre> genreWriter) {
        return stepBuilderFactory.get("importGenre")
                .<Genre, Genre>chunk(CHUNK_SIZE)
                .reader(tmpGenreReader)
                .writer(genreWriter)
                .build();
    }

    @Bean
    public Step importBookGenre(@Qualifier("tmpGenreReader") ItemReader<Genre> tmpGenreReader,
                                 @Qualifier("bookGenresWriter") ItemWriter<Genre> bookGenresWriter) {
        return stepBuilderFactory.get("importBookGenre")
                .<Genre, Genre>chunk(CHUNK_SIZE)
                .reader(tmpGenreReader)
                .writer(bookGenresWriter)
                .build();
    }

    @Bean
    public MongoItemReader<Book> mongoBookReader(MongoTemplate mongoTemplate) {
        MongoItemReader<Book> mongoItemReader = new MongoItemReader<>();
        mongoItemReader.setTemplate(mongoTemplate);
        mongoItemReader.setName("mongoBookReader");
        mongoItemReader.setQuery("{}");
        mongoItemReader.setTargetType(Book.class);
        mongoItemReader.setSort(Map.of("_id", Sort.Direction.ASC));
        return mongoItemReader;
    }

    @Bean
    public AggregateMongoReader<Genre> genreReader(MongoTemplate mongoTemplate) {
        final AggregateMongoReader<Genre> reader = new AggregateMongoReader<>(mongoTemplate, "genres", Genre.class);
        reader.setName("genreReader");
        return reader;
    }

    @Bean
    public AggregateMongoReader<Author> authorReader(MongoTemplate mongoTemplate) {
        final AggregateMongoReader<Author> reader = new AggregateMongoReader<>(mongoTemplate, "authors", Author.class);
        reader.setName("authorReader");
        return reader;
    }


    @Bean
    public ItemReader<Book> tmpBookReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Book>()
                .name("tmpBookReader")
                .dataSource(dataSource)
                .sql("SELECT id, name, year, description FROM tmp_book")
                .rowMapper(new BeanPropertyRowMapper<>(Book.class))
                .build();
    }

    @Bean
    public ItemReader<Author> tmpAuthorReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Author>()
                .name("tmpAuthorReader")
                .dataSource(dataSource)
                .sql("SELECT a.id as id, a.name, b.id as bookId " +
                        "FROM tmp_author a left join tmp_book b on a.book_id = b.ext_id")
                .rowMapper(new BeanPropertyRowMapper<>(Author.class))
                .build();
    }

    @Bean
    public ItemReader<Genre> tmpGenreReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Genre>()
                .name("tmpGenreReader")
                .dataSource(dataSource)
                .sql("SELECT a.id as id, a.name as name, b.id as bookId " +
                        "FROM tmp_genre a left join tmp_book b on a.book_id = b.ext_id")
                .rowMapper(new BeanPropertyRowMapper<>(Genre.class))
                .build();
    }

    @Bean
    public Step dropTempTables(JdbcTemplate jdbcTemplate) {
        return stepBuilderFactory.get("dropTempTables").tasklet((stepContribution, chunkContext) -> {
            jdbcTemplate.execute("drop table if exists tmp_author cascade");
            jdbcTemplate.execute("drop table if exists tmp_genre cascade");
            jdbcTemplate.execute("drop table if exists tmp_book cascade");
            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public Step createTempTables(JdbcTemplate jdbcTemplate) {
        return this.stepBuilderFactory.get("createTempTables").tasklet((stepContribution, chunkContext) -> {
            jdbcTemplate.execute("drop table if exists tmp_author cascade");
            jdbcTemplate.execute("drop table if exists tmp_genre cascade");
            jdbcTemplate.execute("drop table if exists tmp_book cascade");

            jdbcTemplate.execute("create table tmp_book (" +
                    "id bigint auto_increment primary key," +
                    "ext_id varchar(36) not null," +
                    "name varchar(255) not null," +
                    "year varchar(4) not null," +
                    "description varchar(255) not null" +
                    ")");


            jdbcTemplate.execute("create table tmp_author (" +
                    "id bigint auto_increment primary key," +
                    "ext_id varchar(36) not null," +
                    "name varchar(255) not null," +
                    "book_id varchar(36)" +
                    ")");

            jdbcTemplate.execute("create table tmp_genre (" +
                    "id bigint auto_increment primary key," +
                    "ext_id varchar(36) not null," +
                    "name varchar(255) not null," +
                    "book_id varchar(36)" +
                    ")");

            return RepeatStatus.FINISHED;
        }).build();
    }

    @Bean
    public ItemWriter<Book> bookWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Book> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO book " +
                "(id, name, year, description) " +
                "VALUES (:id, :name, :year, :description)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public ItemWriter<Author> authorWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Author> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO author " +
                "(id, name) " +
                "VALUES (:id, :name)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public ItemWriter<Author> bookAuthorWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Author> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO book_author " +
                "(author_id, book_id) " +
                "VALUES (:id, :bookId)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public ItemWriter<Genre> genreWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Genre> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO genre " +
                "(id, name) " +
                "VALUES (:id, :name)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public ItemWriter<Genre> bookGenresWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Genre> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO book_genre " +
                "(genre_id, book_id) " +
                "VALUES (:id, :bookId)");
        writer.setDataSource(dataSource);
        return writer;
    }


    @Bean
    public ItemWriter<Author> tmpAuthorWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Author> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO tmp_author " +
                "(ext_id, name, book_id) " +
                "VALUES (:id, :name, :bookId)");
        writer.setDataSource(dataSource);
        return writer;
    }


    @Bean
    public ItemWriter<Genre> tmpGenreWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Genre> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO tmp_genre " +
                "(ext_id, name, book_id) " +
                "VALUES (:id, :name, :bookId)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public ItemWriter<Book> tmpBookWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Book> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO tmp_book " +
                "(ext_id, name,year, description) " +
                "VALUES (:id, :name, :year, :description)");
        writer.setDataSource(dataSource);
        return writer;
    }

}
