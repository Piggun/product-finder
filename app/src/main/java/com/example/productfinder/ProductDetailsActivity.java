package com.example.productfinder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Product selectedProduct = (Product) getIntent().getSerializableExtra("product");
        TextView productDescriptionView = findViewById(R.id.ProductDescription);
        productDescriptionView.setText(selectedProduct.getDescription());

        TextView productIDView = findViewById(R.id.IDValue);
        productIDView.setText(selectedProduct.getId());

        TextView productCategoryView = findViewById(R.id.categoryValue);
        productCategoryView.setText(String.valueOf(selectedProduct.getCategory()));

        TextView productSubCategoryView = findViewById(R.id.subCategoryValue);
        productSubCategoryView.setText(String.valueOf(selectedProduct.getSubCategory()));

        TextView productPriceView = findViewById(R.id.priceValue);
        // Format the price as a string with two decimal places
        productPriceView.setText(String.format("£%.2f", selectedProduct.getPrice()));

        TextView productBarcodeView = findViewById(R.id.barcodeValue);
        productBarcodeView.setText(selectedProduct.getBarcode());


    }
}