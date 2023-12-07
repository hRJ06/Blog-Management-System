package com.Hindol.Blog.Service;

import com.Hindol.Blog.Payload.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO,Integer postId);
    void deleteComment(Integer commentId);
}
