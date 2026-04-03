package org.example.muallimeduazbackend.repository;

import org.example.muallimeduazbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findById(int id);

}
