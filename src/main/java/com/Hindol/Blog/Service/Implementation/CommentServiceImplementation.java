package com.Hindol.Blog.Service.Implementation;

import com.Hindol.Blog.Entity.Comment;
import com.Hindol.Blog.Entity.Post;
import com.Hindol.Blog.Exception.ResourceNotFoundException;
import com.Hindol.Blog.Payload.CommentDTO;
import com.Hindol.Blog.Repository.CommentRepository;
import com.Hindol.Blog.Repository.PostRepository;
import com.Hindol.Blog.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplementation implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post ID",postId));
        Comment comment = this.modelMapper.map(commentDTO,Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepository.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","Comment ID",commentId));
        this.commentRepository.delete(comment);

    }
}
