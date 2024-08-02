package com.example.demobook.module.supplier.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateRequest {
    String id;
    String appId;
    String name;
    String address;
    String tel;
    String email;
    String description;
}
