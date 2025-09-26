package com.example.CURD_java_project.service.Impl;

import com.example.CURD_java_project.dto.RegisterDTO;
import com.example.CURD_java_project.model.Role;
import com.example.CURD_java_project.model.User;
import com.example.CURD_java_project.repository.OrderRepository;
import com.example.CURD_java_project.repository.ProductRepository;
import com.example.CURD_java_project.repository.RoleRepository;
import com.example.CURD_java_project.repository.UserRepository;
import com.example.CURD_java_project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class IUserService implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public IUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, OrderRepository orderRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public User saveUser(User user) {
//        if(user.getRole() != null || user.getRole().getId() == null){
//            roleRepository.save(user.getRole());
//        }
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserDetail(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        if(id != null){
            userRepository.deleteById(id);
        }
        else {
            System.out.println("system not User");
        }
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public User convertRegisterDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        if(Objects.nonNull(registerDTO)){
            user.setName(registerDTO.getFirstName() + " " +registerDTO.getLastName());
            user.setEmail(registerDTO.getEmail());
            String hashPassword = this.passwordEncoder.encode(registerDTO.getPassword());
            user.setPassword(hashPassword);
            user.setRole(this.getRoleByName("USER"));
        }
        this.saveUser(user);
        return user;
    }

    @Override
    public Boolean CheckExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    @Override
    public long getCountUser() {
        return this.userRepository.count();
    }

    @Override
    public long getCountProduct() {
        return this.productRepository.count();
    }

    @Override
    public long getCountOrder() {
        return this.orderRepository.count();
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User userChangePassword = userRepository.findUserByEmail(email);
        if(userChangePassword != null){
           userChangePassword.setResetPasswordToken(token);
           userRepository.saveAndFlush(userChangePassword);
        }
        else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }
}
