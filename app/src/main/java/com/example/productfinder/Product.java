package com.example.productfinder;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;


public class Product implements Serializable {
    private String id;
    private String description;
    private String barcode;
    private double price;
    private int category;
    private int subCategory;

    public Product(String id, String description, String barcode, double price, int category, int subCategory) throws InvalidProductException{
        setId(id);
        setDescription(description);
        setBarcode(barcode);
        setPrice(price);
        setCategory(category);
        setSubCategory(subCategory);
    }

    public Product(JSONObject json) throws Exception{
        try {
            this.id = json.getString("id");
            this.description = json.getString("description");
            this.barcode = json.getString("barcode");
            this.price = json.getDouble("price");
        } catch (JSONException e) {
            throw new JSONException(e.getMessage());
        }
    }

    @NonNull
    @Override
    public String toString() {
        return this.getDescription() + "\n Category: " + category + " \n Sub-category: " + subCategory;
    }

    // Getters
    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getBarcode() {
        return barcode;
    }
    public double getPrice() {
        return price;
    }
    public int getCategory() {return category; }
    public int getSubCategory() {return subCategory; }


    // Setters
    public void setId(String id) throws InvalidProductException{
        if (id.isEmpty()){
            throw new InvalidProductException("ID can't be blank");
        }
        else if (id.length() != 5){
            throw new InvalidProductException("ID must be 5 characters long");
        }
        else {
            this.id = id;
        }
    }

    public void setDescription(String description) {
        if (description.isEmpty()){
            throw new InvalidProductException("Description can't be blank");
        }
        else if (description.length() > 30) {
            throw new InvalidProductException("Description must be 30 characters or less");
        }
        else {
            this.description = description;
        }
    }

    public void setBarcode(String barcode) {
        if (barcode.isEmpty()){
            throw new InvalidProductException("Barcode can't be blank");
        } else if (barcode.length() != 13) {
            throw new InvalidProductException("Barcode must be 13 characters long");
        }
        else {
            this.barcode = barcode;
        }
    }

    public void setPrice(double price) throws InvalidProductException{
        if (price <= 0) {
            throw new InvalidProductException("Price must be greater than 0");
        }
        else if (BigDecimal.valueOf(price).scale() > 2) {
            throw new InvalidProductException("Price can't have more than 2 decimal places");
        }
        else {
            this.price = price;
        }
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

}
