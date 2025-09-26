package com.example.CURD_java_project.controller.admin;

import com.example.CURD_java_project.export.ProductExcelExporter;
import com.example.CURD_java_project.model.Product;
import com.example.CURD_java_project.service.Impl.IProductService;
import com.example.CURD_java_project.service.Impl.IUploadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private final IUploadService iUploadService;

    private final IProductService iProductService;

    public ProductController(IUploadService iUploadService, IProductService iProductService) {
        this.iUploadService = iUploadService;
        this.iProductService = iProductService;
    }

    @GetMapping("/admin/product/list")
    public String getDashBoardProductAdmin(Model model, @RequestParam("page") Optional<String> pageOptional){
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
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by("id").ascending());
        Page<Product> prs = iProductService.getAllProduct(pageable);
        List<Product> productList = prs.getContent();
        model.addAttribute("listProducts", productList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "/admin/product/product";
    }

    @GetMapping("/admin/product/create-product")
    public String getPageProduct(Model model){
        model.addAttribute("productCreate", new Product());
        return "admin/product/createProduct";
    }

    @PostMapping("/admin/product")
    public String createProduct(@Valid @ModelAttribute("productCreate") Product product, BindingResult bindingResult,
                                @RequestParam("avatarFile") MultipartFile file){
        System.out.println("Create new Product" + product);

        if(bindingResult.hasErrors()){
            return "admin/product/createProduct";
        }
        if(!file.isEmpty()){
            String avatar = iUploadService.handleSaveUploadFile(file, "product");

            product.setImage(avatar);
        }

        iProductService.saveProduct(product);

        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetail(@PathVariable long id, Model model){
        Optional<Product> product = iProductService.getProductById(id);
        System.out.println("Product detail: " + product.get());
        model.addAttribute("product", product.get());
        return "/admin/product/showDetailProduct";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getProductUpdate(@PathVariable long id, Model model){
        Optional<Product> productUpdate = iProductService.getProductById(id);
        model.addAttribute("productUpdate", productUpdate.get());
        return "/admin/product/updateProduct";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@Valid @ModelAttribute("productUpdate") Product productUpdate, BindingResult bindingResult,
                                      @RequestParam("avatarFile") MultipartFile file){

        Product currentProduct = iProductService.getProductById(productUpdate.getId()).get();
        if(bindingResult.hasErrors()){
            return "/admin/product/updateProduct";
        }

        if (currentProduct != null) {
            // update new image
            if (!file.isEmpty()) {
                String img = this.iUploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(productUpdate.getName());
            currentProduct.setPrice(productUpdate.getPrice());
            currentProduct.setQuantity(productUpdate.getQuantity());
            currentProduct.setDetailDesc(productUpdate.getDetailDesc());
            currentProduct.setShortDesc(productUpdate.getShortDesc());
            currentProduct.setFactory(productUpdate.getFactory());
            currentProduct.setTarget(productUpdate.getTarget());

            this.iProductService.saveProduct(currentProduct);
        }

        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getProductDeleteById(Model model, @PathVariable Long id){
        Product currentProduct = iProductService.getProductById(id).get();
        model.addAttribute("id",id);
        model.addAttribute("productDelete", currentProduct);
        return "/admin/product/deletePage";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProductById(@ModelAttribute("productDelete") Product productDelete){
        Product product = iProductService.getProductById(productDelete.getId()).get();
        iProductService.deleteProductById(product.getId());
        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/product/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=products.xlsx";
        response.setHeader(headerKey, headerValue);

        List<Product> products = iProductService.getAllProduct();

        ProductExcelExporter excelExporter = new ProductExcelExporter(products);
        excelExporter.export(response);
    }


}
