package com.example.CURD_java_project.controller.client;

import com.example.CURD_java_project.dto.ProductCriteriaDTO;
import com.example.CURD_java_project.dto.RegisterDTO;
import com.example.CURD_java_project.model.Product;
import com.example.CURD_java_project.model.Product_;
import com.example.CURD_java_project.model.User;
import com.example.CURD_java_project.service.EmailService;
import com.example.CURD_java_project.service.Impl.IProductService;
import com.example.CURD_java_project.service.Impl.IUserService;
import com.example.CURD_java_project.util.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class HomePageClientController {
    private final IProductService iProductService;
    private final IUserService userService;
    private final JavaMailSender mailSender;
    private final EmailService emailService;

    public HomePageClientController(IProductService iProductService, IUserService userService, JavaMailSender mailSender, EmailService emailService) {
        this.iProductService = iProductService;
        this.userService = userService;
        this.mailSender = mailSender;
        this.emailService = emailService;
    }

    @GetMapping("/homepage")
    public String getHomePageClient(Model model){
        Pageable pageable = PageRequest.of(0,10);
        Page<Product> prs = this.iProductService.getAllProduct(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        return "client/homepage/homePageClient";
    }
    @GetMapping("/products")
    public String getProductPage(Model model, ProductCriteriaDTO productCriteriaDTO
                                 ) {
        int page = 1;
        try {

            if (productCriteriaDTO.getPage().isPresent()) {
                // convert from String to int
                page = Integer.parseInt(productCriteriaDTO.getPage().get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }

        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("id").ascending());
//        String name = nameOptional.isPresent() ? nameOptional.get() : nameOptional.orElse("");
//        double min = minOptional.isPresent() ? Double.parseDouble(minOptional.get()) : 0;
//        double max = maxOptional.isPresent() ? Double.parseDouble(maxOptional.get()) : 0;
//        String factory = factoryOptional.isPresent() ? nameOptional.get() : nameOptional.orElse("");
//        double price = priceOptional.isPresent() ? Double.parseDouble(nameOptional.get()) : 0;


        if(productCriteriaDTO.getSort() != null && productCriteriaDTO.getSort().isPresent()){
            String sort = productCriteriaDTO.getSort().get();
            if(sort.equals("gia-tang-dan")){
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.PRICE).ascending());
            }else if (sort.equals("gia-giam-dan")) {
                pageable = PageRequest.of(page - 1, 10, Sort.by(Product_.PRICE).descending());
            }
        }
        Page<Product> prs = this.iProductService.getAllProduct(pageable,productCriteriaDTO);

        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "client/product/products";
    }

    @GetMapping("/register")
    public String getPageRegister(Model model){
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "client/auth/register";
        }
         User user = this.userService.convertRegisterDTOtoUser(registerDTO);
         return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "client/auth/login";
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "client/auth/forgotPassword";
    }

    @PostMapping("/forgot_password")
    public String sendEmailPassword(@RequestParam("email") String email,HttpServletRequest request, RedirectAttributes ra, Model model){
        if (email == null || email.isBlank()) {
            ra.addFlashAttribute("error", "Please enter your email.");
            return "redirect:/forgot_password";
        }

        String token = RandomString.make(30);
        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            emailService.sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
        return "redirect:/forgot_password";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam(value = "token") String token,HttpSession session, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "client/auth/status";
        }
        session.setAttribute("RESET_TOKEN", token);


        return "client/auth/resetPassword";
    }

    @PostMapping("/reset_password")
    public String processResetPassword( HttpServletRequest request, HttpSession session, Model model) {
        String token = (String) session.getAttribute("RESET_TOKEN");
        String password = request.getParameter("password");

        User customer = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (customer == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(customer, password);
            session.removeAttribute("RESET_TOKEN");

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "client/auth/status";
    }

    @GetMapping("/access-deny")
    public String getDenyPermissionPage(){
        return "client/auth/deny";
    }
}
