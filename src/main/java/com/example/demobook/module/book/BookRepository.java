package com.example.demobook.module.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository
        extends JpaRepository<BookEntity, String>,
                JpaSpecificationExecutor<BookEntity>,
                RevisionRepository<BookEntity, String, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, String id);
}
