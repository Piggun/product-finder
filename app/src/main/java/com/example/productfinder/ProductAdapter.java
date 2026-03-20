package com.example.productfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_product, parent, false);
        }

        Product product = getItem(position);

        TextView productDescription = convertView.findViewById(R.id.textView2);
        productDescription.setText(product.getDescription());

        TextView productPrice = convertView.findViewById(R.id.textView16);
        // Format the price as a string with two decimal places
        productPrice.setText(String.format("£%.2f", product.getPrice()));

        TextView productCategory = convertView.findViewById(R.id.textView3);
        productCategory.setText("Category: " + product.getCategory());

        TextView productSubCategory = convertView.findViewById(R.id.textView7);
        productSubCategory.setText("Sub-Category: " + product.getSubCategory());

        return convertView;



    }
}
