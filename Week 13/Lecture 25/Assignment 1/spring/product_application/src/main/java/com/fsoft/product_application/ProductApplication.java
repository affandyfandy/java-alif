package com.fsoft.product_application;

import com.fsoft.product_application.config.RsaKeyConfigProperties;
import com.fsoft.product_application.model.Product;
import com.fsoft.product_application.model.Status;
import com.fsoft.product_application.model.User;
import com.fsoft.product_application.repository.ProductRepository;
import com.fsoft.product_application.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ProductRepository productRepository) {
        return args -> {
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Set.of("USER"));

            userRepository.save(user);

            Product product = new Product();
            product.setName("Product 1");
            product.setPrice((double) 100);
            product.setStatus(Status.ACTIVE);

            productRepository.save(product);
        };
    }
}
