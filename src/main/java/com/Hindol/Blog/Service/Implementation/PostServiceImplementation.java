package com.Hindol.Blog.Service.Implementation;

import com.Hindol.Blog.Entity.Category;
import com.Hindol.Blog.Entity.Post;
import com.Hindol.Blog.Entity.User;
import com.Hindol.Blog.Exception.ResourceNotFoundException;
import com.Hindol.Blog.Payload.PostDTO;
import com.Hindol.Blog.Payload.PostResponse;
import com.Hindol.Blog.Repository.CategoryRepository;
import com.Hindol.Blog.Repository.PostRepository;
import com.Hindol.Blog.Repository.UserRepository;
import com.Hindol.Blog.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImplementation implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO,String email,Integer categoryId) {
        //        FIND THE USER
        User user = this.userRepository.findByEmail(email);
        //        FIND THE CATEGORY
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
        //        CREATE THE POST
        Post post = this.modelMapper.map(postDTO,Post.class);
        //        IMAGE
        post.setImageName("default.png");
        //        DATE
        post.setAddedDate(new Date());
        //        USER
        post.setUser(user);
        //        CATEGORY
        post.setCategory(category);
        Post newPost = this.postRepository.save(post);
        return this.modelMapper.map(newPost,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post ID",postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());
        Post updatedPost =  this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post ID",postId));
        this.postRepository.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDirection) {
        Sort sort = null;
        if(sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();
        }
        org.springframework.data.domain.Pageable p = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> posts = pagePost.getContent();
        List<PostDTO> postDTOS = posts.stream().map((post) -> this.modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post ID",postId));
        return this.modelMapper.map(post,PostDTO.class);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
        List<Post> posts = this.postRepository.findByCategory(category);
        List<PostDTO> postDTOS = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return postDTOS;
    }

    @Override
    public List<PostDTO> getPostsByUser(String email) {
        User user = this.userRepository.findByEmail(email);
        List<Post> posts = this.postRepository.findByUser(user);
        List<PostDTO> postDTOS = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return postDTOS;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> searchedPost = this.postRepository.findByTitleContaining(keyword);
        List<PostDTO> searchedPostDTO = searchedPost.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
        return searchedPostDTO;
    }
}
