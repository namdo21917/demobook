package com.example.demobook.module.base.dto.response.fe.table;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SortableResponse {
    String[] sortDirections = new String[] {"ascend", "descend"};
}
