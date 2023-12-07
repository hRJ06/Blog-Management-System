package com.Hindol.Blog.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenValidationResultDTO {
    private String result;
    private String email;
}
