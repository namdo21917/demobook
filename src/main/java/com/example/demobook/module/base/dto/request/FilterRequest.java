package com.example.demobook.module.base.dto.request;

import java.util.List;

import com.example.demobook.module.base.dto.SearchCriteria;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilterRequest {
    List<SearchCriteria> searchCriteriaList;
}
