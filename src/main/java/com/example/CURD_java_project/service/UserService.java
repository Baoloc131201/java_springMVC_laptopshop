package com.example.CURD_java_project.service;

import com.example.CURD_java_project.dto.RegisterDTO;
import com.example.CURD_java_project.model.Role;
import com.example.CURD_java_project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    List<User> getAllUser();

    Page<User> getAllUser(Pageable pageable);

    User getUserDetail(Long id);

    void deleteUserById(Long id);

    Role getRoleByName(String name);

    User convertRegisterDTOtoUser(RegisterDTO registerDTO);

    Boolean CheckExistEmail(String email);

    User findByEmail(String email);

    long getCountUser();

    long getCountProduct();

    long getCountOrder();

    void updatePassword(User user, String newPassword);

    void updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);
}
