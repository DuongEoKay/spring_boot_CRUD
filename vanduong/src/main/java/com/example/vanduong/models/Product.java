package com.example.vanduong.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Locale;

@Entity
@Table(name="tblProduct")
public class Product {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(
            name="product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;


    @Column(nullable = false, unique = true, length = 300)
    private String name;
    private double price;
    private String date;
    private List<String> img;

    public Product() {

    }



    public Product(String name, double price, String date, List<String> img) {

        //id auto generated
        this.name = name;
        this.price = price;
        this.date = date;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", img=" + img +
                '}';
    }


}
