package com.example.CURD_java_project.controller.admin;

import com.example.CURD_java_project.model.User;
import com.example.CURD_java_project.service.Impl.IUploadService;
import com.example.CURD_java_project.service.Impl.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class UserController {

    private final IUserService iUserService;
    private final IUploadService iUploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(IUserService iUserService, IUploadService uploadService, PasswordEncoder passwordEncoder) {
        this.iUserService = iUserService;
        this.iUploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/admin/user/list")
    public String getAllUser(Model model, @RequestParam("page") Optional<String> pageOptional){
        int page = 1;
        try {

            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }
        Pageable pageable = PageRequest.of(page - 1, 1, Sort.by("id").ascending());
        Page<User> userListPage = iUserService.getAllUser(pageable);
        List<User> listUser = userListPage.getContent();
        System.out.println("List user: " + listUser);
        model.addAttribute("listUsers", listUser);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userListPage.getTotalPages());
        return "admin/user/homePage";
    }

    @GetMapping("/admin/user/create-user")
    public String getHomePage(Model model){
        model.addAttribute("userCreate", new User());
        return "admin/user/createUser";
    }

    @PostMapping("/admin/user")
    public String createUser(@Valid @ModelAttribute ("userCreate") User user, BindingResult bindingResult, @RequestParam("avatarFile")MultipartFile file){
        System.out.println("User Created: " + user);

        if (bindingResult.hasErrors()) {
            return "admin/user/createUser";
        }
        String avatar = iUploadService.handleSaveUploadFile(file, "avatar");

        if(Objects.nonNull(user)){
            String hashPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPassword);
        }
        user.setAvatar(avatar);
        String getRoleByUserFromClient = user.getRole().getName();
        user.setRole(iUserService.getRoleByName(getRoleByUserFromClient));
        iUserService.saveUser(user);
        return "redirect:/admin/user/list";
    }

    @RequestMapping("/admin/user/detail-user/{id}")
    public String getUserDetail(Model model, @PathVariable Long id){
        User userDetail = iUserService.getUserDetail(id);
        System.out.println("User Detail: "+ userDetail);
        model.addAttribute("userDetail", userDetail);
        return "admin/user/showDetailUser";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUser(Model model, @PathVariable Long id){
        User userDetail = iUserService.getUserDetail(id);
        System.out.println("User Detail: "+ userDetail);
        model.addAttribute("userDetail", userDetail);
        return "admin/user/updateUser";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(@ModelAttribute("userDetail")User user){
        User currentUser = iUserService.getUserDetail(user.getId());
        if(currentUser != null){
            currentUser.setFullName(user.getFullName());
            currentUser.setAddress(user.getAddress());
            currentUser.setPhone(user.getPhone());
            currentUser.setName(user.getName());
            iUserService.saveUser(currentUser);
        }
        return "redirect:/admin/user/list";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserById(Model model, @PathVariable Long id){
        User currentUser = iUserService.getUserDetail(id);
        model.addAttribute("id",id);
        model.addAttribute("userDelete", currentUser);
        return "/admin/user/deletePage";
    }

    @PostMapping("/admin/user/delete")
    public String deleteUserById(@ModelAttribute("userDelete")User user){
        User userDelete = iUserService.getUserDetail(user.getId());
        if(userDelete != null){
            iUserService.deleteUserById(userDelete.getId());
        }
        return "redirect:/admin/user/list";
    }

}
