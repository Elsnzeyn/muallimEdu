package org.example.muallimeduazbackend.service;

import org.example.muallimeduazbackend.dto.request.CreateCategoryRequestDto;
import org.example.muallimeduazbackend.entity.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(CreateCategoryRequestDto body);
    Category findById(int id);
    Category findByName(String name);

    List<Category> getAllCategories();
}
