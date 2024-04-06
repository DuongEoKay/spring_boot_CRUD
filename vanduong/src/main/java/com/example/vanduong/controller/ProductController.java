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

    //get all product
    @GetMapping("")
    List<Product> getAllProducts() {
        return repository.findAll();
    }

    //find product by id
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Product Found !!!", foundProduct)

                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot Found Product with id " + id + "!!!", "")
                );
    }

    //insert product
    @PostMapping(value="/insert")
    ResponseEntity<ResponseObject>insertProduct(@RequestBody Product newProduct)
    {
        List<Product> checkDuplicate=repository.findByName(newProduct.getName().trim());
        if(checkDuplicate.size()>0)
        {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Product name already exist", "")

            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert product successfully", repository.save(newProduct))
        );
    }


    //upsert product by id
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject>updateProduct(@RequestBody Product newProduct, @PathVariable Long id)
    {
        Product updatedProduct=repository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDate(newProduct.getDate());
                    product.setPrice(newProduct.getPrice());
                    product.setImg(newProduct.getImg());
                    return repository.save(product);
                }).orElseGet(()->{
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Product Successfully !!!", updatedProduct)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject>  deleteProduct(@PathVariable Long id)
    {
        boolean exists = repository.existsById(id);
        if(exists)
        {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Product Successfully !!!", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can Not find Product To delete !!!", "")
        );
    }

    

}
