package com.epam.spring.persistence.impl;

import com.epam.spring.model.entity.book.Author;
import com.epam.spring.persistence.CustomAuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class AuthorRepositoryImpl implements CustomAuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> findByBirthDate(LocalDate birthDate) {
        TypedQuery<Author> query = em.createNamedQuery("selectAuthorByBirthDate", Author.class);
        query.setParameter(1, birthDate);

        return query.getResultList();
    }

    @Override
    public List<Author> findByFullNameLike(String fullName) {
        TypedQuery<Author> query = em.createNamedQuery("selectAuthorByFullNameLike", Author.class);
        query.setParameter("fullName", "%" + fullName + "%");

        return query.getResultList();
    }
}
