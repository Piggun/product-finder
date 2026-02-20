package com.example.productfinder;

import static org.junit.Assert.*;

import android.util.Log;

import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    Product product1;

    {
        try {
            product1 = new Product("00001","Paracetamol","0000010555552",1.45,1,1);
        } catch (InvalidProductException e) {
            throw new InvalidProductException(e.getMessage());
        }
    }

    @Test
    public void productConstructor() {
        // Error thrown when instance of a product is created with invalid ID
        assertThrows(InvalidProductException.class, () -> {
            Product product2 = new Product("02", "Ibuprofen", "0000010555663", 1.25,1,1);
        });
    }

    @Test
    public void getId() {
        assertEquals("00001", product1.getId());
    }

    @Test
    public void getDescription() {
        assertEquals("Paracetamol", product1.getDescription());
    }

    @Test
    public void getBarcode() {
        assertEquals("0000010555552", product1.getBarcode());
    }

    @Test
    public void getPrice() {
        assertEquals(1.45, product1.getPrice(), 0.001);
    }

    @Test
    public void setId() {
        try {
            product1.setId("12345");
        } catch (InvalidProductException e) {
            throw new InvalidProductException(e.getMessage());
        }

        // Check if ID is set correctly
        assertEquals("12345",product1.getId());

        // Error thrown when ID is set as blank
        assertThrows(InvalidProductException.class, () -> {
            product1.setId("");
        });

        // Error thrown when ID is set with incorrect length
        assertThrows(InvalidProductException.class, () -> {
            product1.setId("01234567");
        });
    }

    @Test
    public void setDescription() {
        product1.setDescription("Test description");
        // Check if description is set correctly
        assertEquals("Test description", product1.getDescription());

        // Error thrown when description is set as blank
        assertThrows(InvalidProductException.class, () -> {
            product1.setDescription("");
        });

        // Error thrown when description is set with incorrect length
        assertThrows(InvalidProductException.class, () -> {
            product1.setDescription("This is a very long description that exceeds the 30 character limit");
        });
    }

    @Test
    public void setBarcode() {
        product1.setBarcode("1234567890123");
        // Check if barcode is set correctly
        assertEquals("1234567890123", product1.getBarcode());

        // Error thrown when barcode is set as blank
        assertThrows(InvalidProductException.class, () -> {
            product1.setBarcode("");
        });

        // Error thrown when barcode is set with incorrect length
        assertThrows(InvalidProductException.class, () -> {
            product1.setBarcode("1234");
        });
    }

    @Test
    public void setPrice() {
        product1.setPrice(2.00);
        // Check if price is set correctly
        assertEquals(2.00, product1.getPrice(), 0.001);

        // Error thrown when price has more than 2 decimal places
        assertThrows(InvalidProductException.class, () -> {
            product1.setPrice(1.055);
        });
        assertThrows(InvalidProductException.class, () -> {
            product1.setPrice(10.994);
        });
        assertThrows(InvalidProductException.class, () -> {
            product1.setPrice(10.0001);
        });
    }

}