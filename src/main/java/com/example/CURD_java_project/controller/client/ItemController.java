package com.example.CURD_java_project.controller.client;

import com.example.CURD_java_project.model.Cart;
import com.example.CURD_java_project.model.CartDetail;
import com.example.CURD_java_project.model.User;
import com.example.CURD_java_project.service.Impl.IProductService;
import com.example.CURD_java_project.service.Impl.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {
    private final IProductService productService;
    private final IUserService userService;

    public ItemController(IProductService productService, IUserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String getPageCartDetail(Model model, HttpServletRequest httpServletRequest){
        User currentUser = new User();
        HttpSession session = httpServletRequest.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        Cart cart = productService.fetchByUser(currentUser);

        List<CartDetail> listCartDetail = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for(CartDetail cartDetail : listCartDetail){
            totalPrice = totalPrice + (cartDetail.getProduct().getPrice() * cartDetail.getQuantity());
        }

        model.addAttribute("listCartDetail", listCartDetail);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);

        return "client/cart/cartPageClient";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String getProductAddToCart(Model model, @PathVariable long id, HttpServletRequest request){
        HttpSession session = request.getSession(false);

        long productId = id;

        String email = (String) session.getAttribute("email");
        productService.handleAddProductToCart(email,productId,session);

        return "redirect:/homepage";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteProductFromCartDetail(@PathVariable long id, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        long cartDetailId = id;
        this.productService.handleRemoveProductDetailToCart(cartDetailId, session);
        return "redirect:/cart";
    }

    @PostMapping("/update-cart-product")
    public String updateCartProduct(@RequestParam("cartDetailId") long cartDetailId, @RequestParam("quantity") int quantity){
        this.productService.updateCartQuantity(cartDetailId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckoutPage(Model model, HttpServletRequest httpServletRequest){
        User currentUser = new User();
        HttpSession session = httpServletRequest.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> listCartDetail = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : listCartDetail) {
            totalPrice += cd.getProduct().getPrice() * cd.getQuantity();
        }

        model.addAttribute("listCartDetail", listCartDetail);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkoutPage";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckoutPage(@ModelAttribute("cart") Cart cart){
        return "redirect:/checkout";
    }





}
