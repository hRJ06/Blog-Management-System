package com.Hindol.Blog.Controller;

import com.Hindol.Blog.Entity.Comment;
import com.Hindol.Blog.Payload.APIResponse;
import com.Hindol.Blog.Payload.CommentDTO;
import com.Hindol.Blog.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postId) {
        CommentDTO createdComment = this.commentService.createComment(commentDTO,postId);
        return new ResponseEntity<CommentDTO>(createdComment, HttpStatus.CREATED);
    }
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<APIResponse>(new APIResponse("Successfully deleted Comment",true),HttpStatus.OK);
    }
}
