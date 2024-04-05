package com.example.vanduong.database;

import com.example.vanduong.models.Product;
import com.example.vanduong.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    @Bean
    CommandLineRunner initDatabse(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product a = new Product("iphone", 100, "2023", null);
//                Product b = new Product("ipad", 1000, "2024", null);
//                logger.info("insert data: " + productRepository.save(a));
//                logger.info("insert data: " + productRepository.save(b));
            }
        };
    }
}
