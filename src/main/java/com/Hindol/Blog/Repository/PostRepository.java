package com.Hindol.Blog.Repository;

import com.Hindol.Blog.Entity.Category;
import com.Hindol.Blog.Entity.Post;
import com.Hindol.Blog.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}
