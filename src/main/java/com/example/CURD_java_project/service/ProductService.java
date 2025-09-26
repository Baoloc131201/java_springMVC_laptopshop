package com.example.CURD_java_project.service;

import com.example.CURD_java_project.dto.ProductCriteriaDTO;
import com.example.CURD_java_project.model.Cart;
import com.example.CURD_java_project.model.Product;
import com.example.CURD_java_project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> getAllProduct();
    Page<Product> getAllProduct(Pageable page);
    Page<Product> getAllProduct(Pageable page, ProductCriteriaDTO productCriteriaDTO);
    Optional<Product> getProductById(Long id);
    void deleteProductById(Long id);
    void handleAddProductToCart(String email, Long id, HttpSession session);
    Cart fetchByUser(User user);
    void handleRemoveProductDetailToCart(long cartDetailId, HttpSession session);
    void updateCartQuantity(long cartDetailId, int quantity);
}
