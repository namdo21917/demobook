package com.example.demobook.module.base.dto.response.fe.form;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OptionResponse {
    String label;
    Object value;
}
