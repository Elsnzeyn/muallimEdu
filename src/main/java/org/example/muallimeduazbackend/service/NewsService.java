package org.example.muallimeduazbackend.service;

import org.example.muallimeduazbackend.dto.request.CreateNewsRequestDto;
import org.example.muallimeduazbackend.dto.request.GetUserIdRequestDto;
import org.example.muallimeduazbackend.dto.response.NewsResponseDto;
import org.example.muallimeduazbackend.entity.News;
import org.example.muallimeduazbackend.entity.User;

import java.util.List;

public interface NewsService {
    News createNews(CreateNewsRequestDto body, User authUser);
    NewsResponseDto getNews(String slug);
    List<NewsResponseDto> getAllNews();
    void likedNews(String slug);
    String editNews(int newsId, CreateNewsRequestDto body, User authUser);
    String deleteNews(int newsId, User authUser);
    News findNewsByIdOrElse(int id);
}
