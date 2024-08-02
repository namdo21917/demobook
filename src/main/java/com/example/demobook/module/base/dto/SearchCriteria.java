package com.example.demobook.module.base.dto;

import com.example.demobook.module.base.enums.SearchOperation;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;
}
