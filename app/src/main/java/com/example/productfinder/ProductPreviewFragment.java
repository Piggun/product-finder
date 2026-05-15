package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ProductPreviewFragment extends BottomSheetDialogFragment {

    private Product product;

    // Standard way to pass data to a Fragment
    public static ProductPreviewFragment newInstance(Product product) {
        ProductPreviewFragment fragment = new ProductPreviewFragment();
        fragment.product = product;
        return fragment;
    }

    @Override    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This forces the fragment to use our rounded theme
        setStyle(STYLE_NORMAL, R.style.RoundedBottomSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate your details layout
        View v = inflater.inflate(R.layout.activity_product_details, container, false);

        // Bind Data
        LinearLayout productLayout = v.findViewById(R.id.productLayout);
        TextView productDescription = v.findViewById(R.id.ProductDescription);
        productDescription.setText(product.getDescription());
        ((TextView) v.findViewById(R.id.IDValue)).setText(product.getId());
        ((TextView) v.findViewById(R.id.priceValue)).setText(String.format("£%.2f", product.getPrice()));
        ((TextView) v.findViewById(R.id.categoryValue)).setText(String.valueOf(product.getCategory()));
        ((TextView) v.findViewById(R.id.subCategoryValue)).setText(String.valueOf(product.getSubCategory()));
        ((TextView) v.findViewById(R.id.barcodeValue)).setText(product.getBarcode());

        // Remove clickable hint from category layout
        ((LinearLayout) v.findViewById(R.id.productCategoryLayout)).setForeground(null);
        // Remove clickable hint from price layout
        ((LinearLayout) v.findViewById(R.id.productPriceLayout)).setForeground(null);
        // Change Product Description text colour
        productDescription.setTextColor(ContextCompat.getColor(requireContext(), R.color.cream));

        productLayout.setOnClickListener(view -> {
            dismiss(); // Close the sheet
            Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        return v;
    }
}