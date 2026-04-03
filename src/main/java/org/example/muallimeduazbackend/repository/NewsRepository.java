package org.example.muallimeduazbackend.repository;

import org.example.muallimeduazbackend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
    News findBySlug(String slug);
}
