package com.example.CURD_java_project.controller.client;

import com.example.CURD_java_project.model.Product;
import com.example.CURD_java_project.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductControllerClient {

    private final ProductService productService;

    public ProductControllerClient(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/product/{id}")
    public String getProductDetail(Model model, @PathVariable long id){
        Product currentProduct = productService.getProductById(id).get();
        model.addAttribute("id", currentProduct.getId());
        model.addAttribute("product", currentProduct);
        return "client/product/productDetail";
    }

    @GetMapping("/contact")
    public String getContactPage(){
        return "/client/product/contact";
    }
}
