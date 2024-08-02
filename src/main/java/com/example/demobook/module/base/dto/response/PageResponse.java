package com.example.demobook.module.base.dto.response;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    Integer pageNumber;
    Integer pageSize;
    Integer totalPages;
    Long totalElements;
    List<T> records;
}
