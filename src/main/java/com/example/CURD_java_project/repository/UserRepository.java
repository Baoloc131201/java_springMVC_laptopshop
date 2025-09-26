package com.example.CURD_java_project.repository;

import com.example.CURD_java_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User saveAndFlush(User user);
    List<User> findAll();
    User findUserById(Long id);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    User findUserByEmail(String email);
    User findByResetPasswordToken(String token);
}
