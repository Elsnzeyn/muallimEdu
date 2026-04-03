package org.example.muallimeduazbackend.service;

import org.example.muallimeduazbackend.dto.request.CreateCommentRequestDto;
import org.example.muallimeduazbackend.dto.response.CommentResponseDto;
import org.example.muallimeduazbackend.entity.User;

import java.util.List;

public interface CommentService {
    void createComment(int id, CreateCommentRequestDto body);
    List<CommentResponseDto> getComments(int id);

    String deleteComment(int id, User user);

    String applyStatus(int commentId);
}
