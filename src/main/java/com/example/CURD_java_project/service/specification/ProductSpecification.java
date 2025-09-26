package com.example.CURD_java_project.service.specification;

import com.example.CURD_java_project.model.Product;
import com.example.CURD_java_project.model.Product_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {
    public static Specification<Product> nameLike(String name){
        String s = "%" + name + "%";
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME),s));
    }

    public static Specification<Product> minPriceLike(double min){
        return (((root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), min)));
    }

    public static Specification<Product> matchListFactory(List<String> factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
    }

    public static Specification<Product> matchListTarget(List<String> target) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.TARGET)).value(target);
    }

    // case5
    public static Specification<Product> matchPrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.gt(root.get(Product_.PRICE), min),
                criteriaBuilder.le(root.get(Product_.PRICE), max));
    }

    // case6
    public static Specification<Product> matchMultiplePrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                root.get(Product_.PRICE), min, max);
    }
}
