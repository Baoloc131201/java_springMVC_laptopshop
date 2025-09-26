package com.example.CURD_java_project.repository;

import com.example.CURD_java_project.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
