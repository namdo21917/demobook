package com.example.demobook.module.base.dto.response.fe;

import java.util.List;

import com.example.demobook.module.base.dto.response.fe.form.FormFieldResponse;
import com.example.demobook.module.base.dto.response.fe.table.ColumnResponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FEResponse {
    List<ColumnResponse> columns;
    List<FormFieldResponse> formFields;
}
