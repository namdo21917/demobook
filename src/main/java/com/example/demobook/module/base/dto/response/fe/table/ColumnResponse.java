package com.example.demobook.module.base.dto.response.fe.table;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ColumnResponse {
    String title;
    String align = "left";
    Integer index;
    Integer minWidth = 50;
    Integer width = 200;
    String dataIndex;
    String slotName;
    Boolean ellipsis = true;
    Boolean tooltip = true;
    SortableResponse sortable = new SortableResponse();
}
