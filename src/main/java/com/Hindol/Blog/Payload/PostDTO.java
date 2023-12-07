package com.Hindol.Blog.Payload;

import com.Hindol.Blog.Entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Integer postId;
    private String title;
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDTO category;
    private UserDTO user;
    private Set<CommentDTO> comment;
}
