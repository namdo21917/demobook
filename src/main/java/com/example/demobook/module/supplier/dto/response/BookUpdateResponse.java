package com.example.demobook.module.supplier.dto.response;

import java.time.Instant;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateResponse {
    String name;
    String author;

    Instant createdAt;
    String createdBy;
    Instant updatedAt;
    String updatedBy;
}
