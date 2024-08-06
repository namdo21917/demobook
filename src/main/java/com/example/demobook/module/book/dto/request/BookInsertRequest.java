package com.example.demobook.module.book.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookInsertRequest {
    String name;
    String author;
    Integer volume;
    String category;
}
