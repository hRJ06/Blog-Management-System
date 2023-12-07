package com.Hindol.Blog.Service.Implementation;

import com.Hindol.Blog.Entity.Category;
import com.Hindol.Blog.Exception.ResourceNotFoundException;
import com.Hindol.Blog.Payload.CategoryDTO;
import com.Hindol.Blog.Repository.CategoryRepository;
import com.Hindol.Blog.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMapper.map(categoryDTO,Category.class);
        Category createdCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(createdCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category updatedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(updatedCategory,CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
        this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Category ID",categoryId));
        return this.modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream().map(category -> this.modelMapper.map(category,CategoryDTO.class)).collect(Collectors.toList());
    }

}
