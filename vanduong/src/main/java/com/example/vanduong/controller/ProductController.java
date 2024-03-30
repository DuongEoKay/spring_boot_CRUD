package com.example.vanduong.controller;

import com.example.vanduong.models.Product;
import com.example.vanduong.models.ResponseObject;
import com.example.vanduong.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;




    @GetMapping("")
    List<Product> getAllProducts()
    {
        return repository.findAll();
    }



    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id)
    {
        Optional<Product> foundProduct=repository.findById(id);
        return foundProduct.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Product Found !!!",foundProduct)

                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot    Found Product with id "+id +"!!!","")
                );
    }



    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct)
    {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Product Successfully", repository.save(newProduct))
        );
    }




}
