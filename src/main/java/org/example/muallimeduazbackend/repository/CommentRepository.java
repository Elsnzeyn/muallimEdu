package org.example.muallimeduazbackend.repository;

import org.example.muallimeduazbackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByNewsId(int newsId);
}
