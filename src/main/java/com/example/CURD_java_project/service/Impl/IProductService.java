package com.example.CURD_java_project.service.Impl;

import com.example.CURD_java_project.dto.ProductCriteriaDTO;
import com.example.CURD_java_project.model.*;
import com.example.CURD_java_project.repository.CartDetailRepository;
import com.example.CURD_java_project.repository.CartRepository;
import com.example.CURD_java_project.repository.ProductRepository;
import com.example.CURD_java_project.service.ProductService;
import com.example.CURD_java_project.service.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class IProductService implements ProductService {
    private final IUserService userService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public IProductService(IUserService userService, ProductRepository productRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Specification<Product> buildPriceSpecification(List<String> price) {
        Specification<Product> combinedSpec = Specification.where(null); // disconjunction
        for (String p : price) {
            double min = 0;
            double max = 0;

            // Set the appropriate min and max based on the price range string
            switch (p) {
                case "duoi-10-trieu":
                    min = 1;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> rangeSpec = ProductSpecification.matchMultiplePrice(min, max);
                combinedSpec = combinedSpec.or(rangeSpec);
            }
        }

        return combinedSpec;
    }


    @Override
    public Page<Product> getAllProduct(Pageable page, ProductCriteriaDTO productCriteriaDTO) {
        if(productCriteriaDTO.getTarget() == null
                && productCriteriaDTO.getFactory() == null
                && productCriteriaDTO.getPrice() == null) {
            return this.productRepository.findAll(page);
        }
        Specification<Product> combinedSpec = Specification.where(null);

        if (productCriteriaDTO.getTarget() != null && productCriteriaDTO.getTarget().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecification.matchListTarget(productCriteriaDTO.getTarget().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }
        if (productCriteriaDTO.getFactory() != null && productCriteriaDTO.getFactory().isPresent()) {
            Specification<Product> currentSpecs = ProductSpecification.matchListFactory(productCriteriaDTO.getFactory().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
            Specification<Product> currentSpecs = this.buildPriceSpecification(productCriteriaDTO.getPrice().get());
            combinedSpec = combinedSpec.and(currentSpecs);
        }

        return this.productRepository.findAll(combinedSpec, page);
    }

//    @Override
//    public Page<Product> getAllProduct(Pageable page, String name) {
//        return this.productRepository.findAll(ProductSpecification.nameLike(name), page);
//    }

    @Override
    public Optional<Product> getProductById(Long id) {
        if(id == null){
            throw new RuntimeException("id is null");
        }
        return productRepository.findProductById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        if(id == null){
            throw new RuntimeException("id is null");
        }
        productRepository.deleteById(id);
    }

    @Override
    public void handleAddProductToCart(String email, Long id, HttpSession session) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        Cart cart = cartRepository.findByUser(user);
        if(cart == null){
            Cart cartNew = new Cart();
            cartNew.setUser(user);
            cartNew.setSum(0);
            cart = this.cartRepository.save(cartNew);
        }

        Optional<Product> product = productRepository.findProductById(id);


        if(product.isPresent()){

            CartDetail cartDetailOld = cartDetailRepository.findByCartAndProduct(cart,product.get());
            if(cartDetailOld == null){
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(product.get());
                cartDetail.setPrice(product.get().getPrice());
                cartDetail.setQuantity(1);
                cartDetailRepository.save(cartDetail);

                int sumQuantity = cart.getSum() + 1;
                cart.setSum(sumQuantity);
                cartRepository.save(cart);
                session.setAttribute("sumQuantity", sumQuantity);
            }
            else {
                cartDetailOld.setQuantity(cartDetailOld.getQuantity() + 1);
                cartDetailRepository.save(cartDetailOld);
            }

        }
    }

    @Override
    public Cart fetchByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public void handleRemoveProductDetailToCart(long cartDetailId, HttpSession session) {
         Optional<CartDetail> cartDetail = this.cartDetailRepository.findById(cartDetailId);
         if(cartDetail.isPresent()){

             CartDetail currentCartDetail = cartDetail.get();
             Cart cart = currentCartDetail.getCart();

             this.cartDetailRepository.deleteById(currentCartDetail.getId());

             if(cart.getSum() > 1){
                 int sum = cart.getSum() - 1;
                 cart.setSum(sum);
                 session.setAttribute("sumQuantity", sum);
                 this.cartRepository.save(cart);
             }
             else {
                 session.setAttribute("sumQuantity", 0);
                 this.cartRepository.deleteById(cart.getId());
             }
         }

    }

    @Override
    public void updateCartQuantity(long cartDetailId, int quantity) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(cartDetailId);

        if(cartDetailOptional.isPresent()){
            CartDetail currentCartDetail = cartDetailOptional.get();
            currentCartDetail.setQuantity(quantity);
            currentCartDetail.setPrice(currentCartDetail.getPrice() * quantity);
            this.cartDetailRepository.save(currentCartDetail);
        }
    }


}
