package com.example.CURD_java_project.repository;

import com.example.CURD_java_project.model.Cart;
import com.example.CURD_java_project.model.CartDetail;
import com.example.CURD_java_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Long> {
    CartDetail save(CartDetail cartDetail);

    CartDetail findByCartAndProduct(Cart cart, Product product);

    Optional<CartDetail> findById(long id);

    void deleteById(long id);
}
