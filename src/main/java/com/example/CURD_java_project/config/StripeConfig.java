package com.example.CURD_java_project.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StripeConfig {
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;
    @PostConstruct
    public void init() { Stripe.apiKey = secretKey; }
}

