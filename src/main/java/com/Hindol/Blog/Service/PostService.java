package com.Hindol.Blog.Service;

import com.Hindol.Blog.Entity.Post;
import com.Hindol.Blog.Payload.PostDTO;
import com.Hindol.Blog.Payload.PostResponse;

import java.util.List;

public interface PostService {
    //    CREATE
    PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId);
    //    UPDATE
    PostDTO updatePost(PostDTO postDTO,Integer postId);
    //    DELETE
    void deletePost(Integer postId);
    //    GET ALL POSTS
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDirection);
    //    GET SINGLE POST
    PostDTO getPostById(Integer postId);
    //    GET ALL POSTS BY CATEGORY
    List<PostDTO> getPostsByCategory(Integer categoryId);
    //    GET ALL POSTS BY USER
    List<PostDTO> getPostsByUser(Integer userId);
    //    SEARCH POSTS
    List<PostDTO> searchPosts(String keyword);
}
