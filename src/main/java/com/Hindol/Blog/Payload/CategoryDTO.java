package com.Hindol.Blog.Payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Integer categoryId;
    @NotEmpty
    @Size(min = 4 ,message = "Category Title must be at least of 4 characters")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 10, message = "Category Description must be at least of 4 characters")
    private String categoryDescription;
}
