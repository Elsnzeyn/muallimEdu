package org.example.muallimeduazbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.CreateCategoryRequestDto;
import org.example.muallimeduazbackend.entity.Category;
import org.example.muallimeduazbackend.exception.ResourceNotFoundException;
import org.example.muallimeduazbackend.repository.CategoryRepository;
import org.example.muallimeduazbackend.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(CreateCategoryRequestDto body){
        if(body == null){
            throw new ResourceNotFoundException("category not found");
        }
        var category = Category.builder().name(body.getName()).build();
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Override
    public Category findByName(String name){
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findById(int id){
        return categoryRepository.findById(id).get();
    }


}
