package com.example.demobook.module.base.dto.response.fe.form;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormFieldResponse {
    String type;
    String label;
    String field;
    Integer span;
    String props;
    String rules;
    List<OptionResponse> options;
}
