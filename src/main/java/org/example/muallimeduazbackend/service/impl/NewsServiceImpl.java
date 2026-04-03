package org.example.muallimeduazbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.CreateNewsRequestDto;
import org.example.muallimeduazbackend.dto.response.NewsResponseDto;
import org.example.muallimeduazbackend.entity.News;
import org.example.muallimeduazbackend.entity.User;
import org.example.muallimeduazbackend.exception.ResourceNotFoundException;
import org.example.muallimeduazbackend.repository.NewsRepository;
import org.example.muallimeduazbackend.service.CategoryService;
import org.example.muallimeduazbackend.service.NewsService;
import org.springframework.stereotype.Service;
import java.util.List;

import static org.example.muallimeduazbackend.enums.UserRoleEnum.SUPER_ADMIN;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final CategoryService categoryService;


    @Override
    public News createNews(CreateNewsRequestDto body, User authUser)
    {
        newsValidation(body);
        var category = categoryService.findById(body.getCategoryId());
        var news = News.builder()
                .title(body.getTitle())
                .content(body.getContent())
                .category(category)
                .slug(setSlug(body.getTitle()))
                .user(authUser)
                .slug(body.getContent())
                .build();
        return newsRepository.save(news);
    }

    public String setSlug(String title){
        return title.toLowerCase().replaceAll("[^a-z0-9]","")
                .replaceAll("\\s+", "");
    }

    private void newsValidation(CreateNewsRequestDto body){
        if(body.getTitle().length()<5){
            throw new RuntimeException("Title cannot be less than 5 elements");
        }
        if(body.getContent().length()<20){
            throw new RuntimeException("Content cannot be less 10 elements");
        }

    }

    @Override
    public NewsResponseDto getNews(String slug){
        var news = newsRepository.findBySlug(slug);
        if(news == null){
            throw  new RuntimeException("there is not such a news");
        }
        var currentViewCount = news.getViewCount();
        news.setViewCount(currentViewCount + 1);
        var savedNews = newsRepository.save(news);
        return NewsResponseDto.builder()
                .id(news.getId())
                .content(savedNews.getContent())
                .title(savedNews.getTitle())
                .viewCount(savedNews.getViewCount())
                .likeCount(savedNews.getLikeCount())
                .categoryId(savedNews.getCategory().getId())
                .categoryName(savedNews.getCategory().getName())
                .build();
    }

    @Override
    public List<NewsResponseDto> getAllNews(){
        return newsRepository.findAll().stream().map(s -> NewsResponseDto.builder()
                .id(s.getId())
                .content(s.getContent())
                .title(s.getTitle())
                .viewCount(s.getViewCount())
                .likeCount(s.getLikeCount())
                .build()).toList();
    }

    @Override
    public void likedNews(String slug){
        var news = newsRepository.findBySlug(slug);
        if(news == null){
            throw new RuntimeException("news not found");
        }
        int countLike = news.getLikeCount();
        news.setLikeCount(countLike + 1);
        newsRepository.save(news);

    }

//    private List<NewsResponseDto> mapToResponseDto(List<News> body){
//        List<NewsResponseDto> result = new ArrayList<>();
//        for(News s : body){
//             result.add(NewsResponseDto.builder()
//                    .id(s.getId())
//                    .content(s.getContent())
//                    .title(s.getTitle())
//                    .viewCount(s.getViewCount())
//                    .likeCount(s.getLikeCount())
//                    .build());}
//            return result;
//    }

    @Override
    public String editNews(int id, CreateNewsRequestDto body, User authUser){
        var news = getNewsOrElseThrow(id);


        var userRole = authUser.getRole();
        var userRoleFind = news.getUser().getRole();
        if(userRole.equals(SUPER_ADMIN.name()) || userRole.equals(userRoleFind)){
            newsRepository.save(News.builder()
                    .id(id)
                    .title(body.getTitle())
                    .content(body.getContent())
                    .user(authUser)
                    .build());
            return "changed successfully";
        }
        return "Your role is not enough to edit";
    }

    @Override
    public String deleteNews(int newsId, User authUser){
        var news = getNewsOrElseThrow(newsId);

        var userRole = news.getUser().getRole();
        if(authUser.getRole().equals(SUPER_ADMIN.name()) || userRole.equals(authUser.getRole())){
            newsRepository.deleteById(newsId);
            return "deleted successfully";
        }
        return "Your role is not enough to delete the news";
    }

    private News getNewsOrElseThrow(int id){
        return newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("news not found" ));
    }

    @Override
    public News findNewsByIdOrElse(int id){
        return newsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("news not found"));
    }


}
