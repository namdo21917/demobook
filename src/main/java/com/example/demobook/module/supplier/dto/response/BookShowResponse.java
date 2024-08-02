package com.example.demobook.module.supplier.dto.response;

import java.time.Instant;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookShowResponse {
    String id;
    String appId;
    String name;
    String address;
    String tel;
    String email;
    String description;

    Instant createdAt;
    String createdBy;
    Instant updatedAt;
    String updatedBy;
}
