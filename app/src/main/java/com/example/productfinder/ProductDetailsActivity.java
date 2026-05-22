package com.example.productfinder;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
              new MaterialAlertDialogBuilder(this, R.style.DialogStyle)
                    .setTitle("Select Category")
                    .setIcon(R.drawable.category_icon)
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

        // Set long click listener on the Price view to allow changes to product price
        productPriceLayout.setOnLongClickListener(v -> {
            // Create the EditText input field
            final android.widget.EditText input = new android.widget.EditText(this);
            input.setInputType(android.view.inputmethod.EditorInfo.TYPE_CLASS_NUMBER | android.view.inputmethod.EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
            input.setHint("0.00");
            input.setMaxWidth(50);
            input.setText(String.valueOf(String.format("%.2f", selectedProduct.getPrice())));

            // Add InputFilter to restrict to 2 decimal places
            input.setFilters(new InputFilter[]{new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    String result = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());
                    if (result.isEmpty()) return null;

                    int dotPos = result.indexOf(".");
                    if (dotPos >= 0) {
                        if (result.length() - dotPos - 1 > 2) return "";
                    }
                    return null;
                }
            }});

            // Wrap the EditText in a FrameLayout to control WIDTH and MARGINS
            android.widget.FrameLayout container = new android.widget.FrameLayout(this);
            android.widget.FrameLayout.LayoutParams params = new android.widget.FrameLayout.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            );

            // Set horizontal margins (e.g., 48dp) to prevent the text box from touching the dialog edges
            int margin = (int) (48 * getResources().getDisplayMetrics().density);
            params.leftMargin = margin;
            params.rightMargin = margin;
            input.setLayoutParams(params);
            input.setTextAlignment(android.view.View.TEXT_ALIGNMENT_CENTER);
            container.addView(input);

            // 2. Create the Dialog
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Change Product Price")
                    .setIcon(R.drawable.price_icon)
                    .setView(container) // Add the input box to the dialog
                    .setPositiveButton("Update", (dialog, which) -> {
                        String newPriceText = input.getText().toString();
                        if (!newPriceText.isEmpty()) {
                            try {
                                double newPrice = Double.parseDouble(newPriceText);

                                // Update the Master List (Static)
                                for (Product p : MainActivity.productList) {
                                    if (p.getId().equals(selectedProduct.getId())) {
                                        selectedProduct.setPrice(newPrice); // Also update the local reference
                                        p.setPrice(newPrice);
                                        break;
                                    }
                                }

                                // Update UI
                                productPriceView.setText(String.format("£%.2f", newPrice));

                                android.widget.Toast.makeText(this, "Price updated", android.widget.Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                android.widget.Toast.makeText(this, "Invalid price entered", android.widget.Toast.LENGTH_SHORT).show();
                            } catch (InvalidProductException e) {
                                android.widget.Toast.makeText(this, e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                android.widget.Toast.makeText(this, "An error occurred", android.widget.Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            android.widget.Toast.makeText(this, "No price entered", android.widget.Toast.LENGTH_SHORT).show();
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