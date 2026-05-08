package com.example.productfinder;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

        LinearLayout productCategoryLayout = findViewById(R.id.productCategoryLayout);
        TextView productCategoryView = findViewById(R.id.categoryValue);
        productCategoryView.setText(String.valueOf(selectedProduct.getCategory()));

        TextView productSubCategoryView = findViewById(R.id.subCategoryValue);
        productSubCategoryView.setText(String.valueOf(selectedProduct.getSubCategory()));

        LinearLayout productPriceLayout = findViewById(R.id.productPriceLayout);
        TextView productPriceView = findViewById(R.id.priceValue);
        // Format the price as a string with two decimal places
        productPriceView.setText(String.format("£%.2f", selectedProduct.getPrice()));

        TextView productBarcodeView = findViewById(R.id.barcodeValue);
        productBarcodeView.setText(selectedProduct.getBarcode());

        // Set long click listener on the Category view
        productCategoryLayout.setOnLongClickListener(v -> {
            // Create a Selection Dialog
            new AlertDialog.Builder(ProductDetailsActivity.this)
                    .setTitle("Select Category")
                    .setItems(MainActivity.categories, (dialog, index) -> {
                        // Get selected category
                        String selectedCategory = MainActivity.categories[index];

                        // Update the UI
                        productCategoryView.setText(selectedCategory);

                        // Update the product category in the list
                        for (Product product : MainActivity.productList) {
                            if (product.getId().equals(selectedProduct.getId())) {
                                product.setCategory(Integer.parseInt(selectedCategory));
                                break;
                            }
                        }
                        android.widget.Toast.makeText(this, "Category updated to " + selectedCategory, android.widget.Toast.LENGTH_SHORT).show();
                    })
                    .show();
            // Return true to indicate that the long click event has been handled
            return true;
        });

        productCategoryLayout.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Press and hold to change category", android.widget.Toast.LENGTH_SHORT).show();
        });


        productPriceLayout.setOnLongClickListener(v -> {
            // 1. Create the EditText input field
            final android.widget.EditText input = new android.widget.EditText(this);
            input.setInputType(android.view.inputmethod.EditorInfo.TYPE_CLASS_NUMBER | android.view.inputmethod.EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
            input.setHint("0.00");
            input.setText(String.valueOf(selectedProduct.getPrice()));

            // 2. Create the Dialog
            new AlertDialog.Builder(this)
                    .setTitle("Change Product Price")
                    .setView(input) // Add the input box to the dialog
                    .setPositiveButton("Update", (dialog, which) -> {
                        String newPriceText = input.getText().toString();
                        if (!newPriceText.isEmpty()) {
                            // TODO fix input validation
                            try {
                                double newPrice = Double.parseDouble(newPriceText);

                                // Update UI
                                productPriceView.setText(String.format("£%.2f", newPrice));

                                // Update the Master List (Static)
                                for (Product p : MainActivity.productList) {
                                    if (p.getId().equals(selectedProduct.getId())) {
                                        p.setPrice(newPrice);
                                        break;
                                    }
                                }

                                android.widget.Toast.makeText(this, "Price updated", android.widget.Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                android.widget.Toast.makeText(this, "Invalid price entered", android.widget.Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });


        productPriceLayout.setOnClickListener(v -> {
            android.widget.Toast.makeText(this, "Press and hold to change price", android.widget.Toast.LENGTH_SHORT).show();
        });



    }
}