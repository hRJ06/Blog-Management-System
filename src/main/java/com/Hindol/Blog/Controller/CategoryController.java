package com.Hindol.Blog.Controller;

import com.Hindol.Blog.Payload.APIResponse;
import com.Hindol.Blog.Payload.CategoryDTO;
import com.Hindol.Blog.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory =  this.categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable Integer catId) {
        CategoryDTO updatedCategory =  this.categoryService.updateCategory(categoryDTO,catId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }
    @DeleteMapping("/{catId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Integer catId) {
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<APIResponse>(new APIResponse("Category is deleted successfully",true),HttpStatus.OK);
    }
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer catId) {
        CategoryDTO categoryDTO = this.categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDTO>(categoryDTO,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = this.categoryService.getCategories();
        return new ResponseEntity<List<CategoryDTO>>(categories,HttpStatus.OK);
    }
}
