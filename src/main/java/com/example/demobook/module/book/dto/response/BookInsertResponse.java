package com.example.demobook.module.book.dto.response;

import java.time.Instant;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookInsertResponse {
    String name;
    String author;
    Integer volume;
    String category;

    Instant createdAt;
    String createdBy;
    Instant updatedAt;
    String updatedBy;
}
