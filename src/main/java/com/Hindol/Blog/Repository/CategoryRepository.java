package com.Hindol.Blog.Repository;

import com.Hindol.Blog.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
