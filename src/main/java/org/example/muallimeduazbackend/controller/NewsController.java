package org.example.muallimeduazbackend.controller;
import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.*;
import org.example.muallimeduazbackend.dto.response.CommentResponseDto;
import org.example.muallimeduazbackend.dto.response.GeneralResponseDto;
import org.example.muallimeduazbackend.dto.response.NewsResponseDto;
import org.example.muallimeduazbackend.entity.News;
import org.example.muallimeduazbackend.service.CommentService;
import org.example.muallimeduazbackend.service.JwtService;
import org.example.muallimeduazbackend.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController{
    private final NewsService newsService;
    private final CommentService commentService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<GeneralResponseDto<News>> createNews(@RequestHeader("Authorization") String token, @RequestBody CreateNewsRequestDto body){
        var authUser = jwtService.decodeToken(token);
        var response = new GeneralResponseDto<>("news loaded", newsService.createNews(body, authUser));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<GeneralResponseDto<NewsResponseDto>> getNews(@RequestParam String slug){
        var response = new GeneralResponseDto<>("news loaded", newsService.getNews(slug));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<GeneralResponseDto<List<NewsResponseDto>>> getAllNews(){
        var response = new GeneralResponseDto<>("news loaded", newsService.getAllNews());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{slug}/like")
    public ResponseEntity<?> likedNews(@PathVariable String slug){
        newsService.likedNews(slug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponseDto<?>> editNews(@PathVariable int newsId, @RequestBody CreateNewsRequestDto body, @RequestHeader("Authorization") String token){
        var authUser = jwtService.decodeToken(token);
        var response = new GeneralResponseDto<>("news loaded", newsService.editNews(newsId, body, authUser));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{newsId}")
    public ResponseEntity<GeneralResponseDto<?>> deleteNews(@PathVariable int newsId, @RequestHeader("Authorization") String token){
        var authUser = jwtService.decodeToken(token);
        var response = new GeneralResponseDto<>("news loaded", newsService.deleteNews(newsId, authUser));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{newsId}/comment")
    public ResponseEntity<GeneralResponseDto<?>> createComment(@PathVariable int newsId, @RequestBody CreateCommentRequestDto body){
        commentService.createComment(newsId, body);
        var response = new GeneralResponseDto<>("comment added", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}/comment")
    public ResponseEntity<GeneralResponseDto<String>> applyStatus(@PathVariable int commentId, @RequestHeader("Authorization") String token){
        jwtService.decodeToken(token);
        var response = new GeneralResponseDto<>("status: ", commentService.applyStatus(commentId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{newsId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable int newsId){
        return new ResponseEntity<>(commentService.getComments(newsId), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}/comment")
    public ResponseEntity<GeneralResponseDto<String>> deleteComment(@PathVariable int commentId, @RequestHeader("Authorization") String token){
        var user = jwtService.decodeToken(token);
        var response = new GeneralResponseDto<>("deleted ? ", commentService.deleteComment(commentId, user));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
