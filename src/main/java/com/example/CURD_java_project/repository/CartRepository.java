package com.example.CURD_java_project.repository;

import com.example.CURD_java_project.model.Cart;
import com.example.CURD_java_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
     Cart findByUser(User user);
     Cart save(Cart cart);
}
