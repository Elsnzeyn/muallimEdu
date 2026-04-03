package org.example.muallimeduazbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.CreateCategoryRequestDto;
import org.example.muallimeduazbackend.dto.response.GeneralResponseDto;
import org.example.muallimeduazbackend.entity.Category;
import org.example.muallimeduazbackend.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<GeneralResponseDto<?>> createNews(@RequestBody CreateCategoryRequestDto body){
        categoryService.createCategory(body);
        var response = new GeneralResponseDto<>("news loaded", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GeneralResponseDto<List<Category>>> getAllCategory(){
       var response = new GeneralResponseDto<>("all Categories: ", categoryService.getAllCategories());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
