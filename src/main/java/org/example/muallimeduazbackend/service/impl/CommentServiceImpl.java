package org.example.muallimeduazbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.CreateCommentRequestDto;
import org.example.muallimeduazbackend.dto.response.CommentResponseDto;
import org.example.muallimeduazbackend.entity.Comment;
import org.example.muallimeduazbackend.entity.User;
import org.example.muallimeduazbackend.exception.ResourceNotFoundException;
import org.example.muallimeduazbackend.repository.CommentRepository;
import org.example.muallimeduazbackend.service.CommentService;
import org.example.muallimeduazbackend.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.muallimeduazbackend.enums.UserRoleEnum.ADMIN;
import static org.example.muallimeduazbackend.enums.UserRoleEnum.SUPER_ADMIN;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final NewsService newsService;

    @Override
    public void createComment(int id, CreateCommentRequestDto body){
        var news= newsService.findNewsByIdOrElse(id);
        commentRepository.save(Comment.builder()
                .name(body.getName())
                .surname(body.getSurname())
                .content(body.getContent())
                .news(news)
                .build());
    }

    @Override
    public List<CommentResponseDto> getComments(int id){
       var comments = commentRepository.findCommentsByNewsId(id);
       List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
       for(Comment comment: comments){
           if(comment.isStatus() == true){
                commentResponseDtos.add(CommentResponseDto.builder()
                        .name(comment.getName())
                        .surname(comment.getSurname())
                        .content(comment.getContent()).build());
           }
       }
       return commentResponseDtos;
    }

    @Override
    public String applyStatus(int commentId){
        var optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isEmpty()){
            throw new ResourceNotFoundException("comment not found");
        }
        var comment = optionalComment.get();
        comment.setStatus(true);
        commentRepository.save(comment);
        return "status changed";
    }

    @Override
    public String deleteComment(int id, User user){
        var comment = commentRepository.findById(id);
        if(comment.isEmpty()){
            throw new ResourceNotFoundException("comment not found");
        }
        if(user.getRole().equals(ADMIN.name()) || user.getRole().equals(SUPER_ADMIN.name())){
            commentRepository.deleteById(id);
            return "deleted";
        }
        return "your role is not enough";
    }

}
