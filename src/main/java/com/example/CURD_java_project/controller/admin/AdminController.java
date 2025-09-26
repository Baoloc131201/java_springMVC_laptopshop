package com.example.CURD_java_project.controller.admin;

import com.example.CURD_java_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getDashBoardAdmin(Model model){
        model.addAttribute("countUsers", this.userService.getCountUser());
        model.addAttribute("countProducts",this.userService.getCountProduct());
        model.addAttribute("countOrders", this.userService.getCountOrder());
        return "/admin/dashboard/dashboard";
    }
}
