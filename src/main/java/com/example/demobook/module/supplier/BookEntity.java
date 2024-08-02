package com.example.demobook.module.supplier;

import jakarta.persistence.*;

import org.hibernate.envers.Audited;

import com.example.demobook.module.base.AuditBase;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audited
@Entity(name = "book")
public class BookEntity extends AuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "name", unique = true)
    String name;

    @Column(name = "author", unique = true)
    String author;
}
