package com.e_commerce.backend_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String description;
    private String name;
    private double price;
    private int stock;

}
