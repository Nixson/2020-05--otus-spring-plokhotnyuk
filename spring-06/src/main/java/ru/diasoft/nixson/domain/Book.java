package ru.diasoft.nixson.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
@NamedEntityGraph(name = "book-entity-graph",
        attributeNodes = {@NamedAttributeNode("author"),@NamedAttributeNode("genre")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author")
    private Author author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genre")
    private Genre genre;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "year", unique = false)
    private String year;

    @Column(name = "description", unique = false)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="book")
    private List<Comment> comments;
}
