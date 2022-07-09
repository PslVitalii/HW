package com.epam.spring.model.entity.book;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NamedQueries({
        @NamedQuery(
                name = "selectAuthorByBirthDate",
                query = "select a from Author a where a.birthDate = ?1"
        ),
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "selectAuthorByFullNameLike",
                query = "select * from author where full_name ilike :fullName",
                resultClass = Author.class
        ),
})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;
    private LocalDate birthDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();
}
