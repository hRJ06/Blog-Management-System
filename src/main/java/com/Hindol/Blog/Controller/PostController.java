package com.Hindol.Blog.Controller;

import com.Hindol.Blog.Config.CONSTANTS;
import com.Hindol.Blog.Entity.Post;
import com.Hindol.Blog.Payload.APIResponse;
import com.Hindol.Blog.Payload.PostDTO;
import com.Hindol.Blog.Payload.PostResponse;
import com.Hindol.Blog.Service.FileService;
import com.Hindol.Blog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    //    CREATE POST
    @PostMapping("/user/{userId}/category/{categoryId}/v1/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable Integer userId,@PathVariable Integer categoryId) {
        PostDTO createPost = this.postService.createPost(postDTO,userId,categoryId);
        return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);
    }
    //    GET POSTS BY USER;
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId) {
        List<PostDTO> postDTOS = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDTO>>(postDTOS,HttpStatus.OK);
    }
    //    GET POSTS BY CATEGORY
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> postDTOS = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDTO>>(postDTOS,HttpStatus.OK);
    }
    //    GET ALL POSTS
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = CONSTANTS.PAGE_NUMBER,required = false) Integer pageNumber, @RequestParam(value = "pageSize",defaultValue = CONSTANTS.PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = "sortBy",defaultValue = CONSTANTS.SORT_BY, required = false) String sortBy, @RequestParam(value = "sortDirection", defaultValue = CONSTANTS.SORT_DIRECTION, required = false) String sortDirection) {
        return new ResponseEntity<PostResponse>(this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDirection ),HttpStatus.OK);
    }
    //    GET POST BY ID
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getAllPosts(@PathVariable Integer postId) {
        return new ResponseEntity<PostDTO>(this.postService.getPostById(postId),HttpStatus.OK);
    }
    //    DELETE POST
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<APIResponse>(new APIResponse("Post is successfully deleted",true),HttpStatus.OK);
    }
    //    UPDATE POST
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,@PathVariable Integer postId) {
        PostDTO updatedPost = this.postService.updatePost(postDTO,postId);
        return new ResponseEntity<PostDTO>(updatedPost,HttpStatus.OK);
    }
    //    SEARCH
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords) {
        List<PostDTO> searchPosts = this.postService.searchPosts(keywords);
        return new ResponseEntity<List<PostDTO>>(searchPosts,HttpStatus.OK);
    }
    //    POST IMAGE UPLOAD
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) {
        PostDTO postDTO = this.postService.getPostById(postId);
        String uploadLink = this.fileService.uploadImage(image);
        postDTO.setImageName(uploadLink);
        PostDTO updatedPost = this.postService.updatePost(postDTO,postId);
        return new ResponseEntity<PostDTO>(updatedPost,HttpStatus.OK);
    }
}
