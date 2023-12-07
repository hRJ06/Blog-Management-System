package com.Hindol.Blog.Repository;

import com.Hindol.Blog.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
