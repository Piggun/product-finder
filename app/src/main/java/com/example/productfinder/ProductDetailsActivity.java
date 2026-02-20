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

//        String productDescription = getIntent().getStringExtra("productDescription");
//        TextView productDescriptionView = findViewById(R.id.ProductDescription);
//        productDescriptionView.setText(productDescription);

        Product selectedProduct = (Product) getIntent().getSerializableExtra("product");
        TextView productDescriptionView = findViewById(R.id.ProductDescription);
        productDescriptionView.setText(selectedProduct.getDescription());

        TextView productIDView = findViewById(R.id.IDValue);
        productIDView.setText(selectedProduct.getId());

        TextView productPriceView = findViewById(R.id.priceValue);
        productPriceView.setText(String.valueOf(selectedProduct.getPrice()));

        TextView productBarcodeView = findViewById(R.id.barcodeValue);
        productBarcodeView.setText(selectedProduct.getBarcode());


    }
}