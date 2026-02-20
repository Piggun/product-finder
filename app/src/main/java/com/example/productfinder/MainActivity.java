package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ListView productListView = (ListView) findViewById(R.id.productsList);
        SearchView searchBar = findViewById(R.id.searchView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Product product1 = null;
        Product product2 = null;
        Product product3 = null;
        Product product4 = null;
        Product product5 = null;
        Product product6 = null;
        Product product7 = null;
        Product product8 = null;
        Product product9 = null;
        Product product10 = null;

        List<Product> productList = new ArrayList<>();

        try {
            product1 = new Product("00001","Paracetamol","0000010555552",1.45, 1,1);
            product2 = new Product("00002","Ibuprofen","0000010666552",1.20,1,1);
            product3 = new Product("00003","Toothpaste","0000010424582",0.99,2,1);
            product4 = new Product("00004","Plates","0000010424592",8.99,3,1);
            product5 = new Product("00005","Snickers Bar","0000010555553",1.09,4,1);
            product6 = new Product("00006","Shampoo","0000010666554",3.99,2,1);
            product7 = new Product("00007","Phone Charger","0000010424589",12.99,5,1);
            product8 = new Product("00008","Broccoli","0000010424595",0.79,4,2);
            product9 = new Product("00009","Carrots","0000010424586",0.69,4,2);
            product10 = new Product("00010","Courgettes","0000010424597",1.09,4,2);

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }


        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);
        productList.add(product7);
        productList.add(product8);
        productList.add(product9);
        productList.add(product10);


        productList.sort(Comparator.comparing(Product::getDescription));



//        ArrayAdapter<Product> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        ProductAdapter adapter = new ProductAdapter(this, productList);

        productListView.setAdapter(adapter);

        // Search bar functionality
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
                }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // List view functionality
        productListView.setOnItemClickListener((parent, view, position, id) -> {
            Product selectedProduct = productList.get(position);
            Toast.makeText(MainActivity.this, "Selected product: " + selectedProduct.getDescription(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra("product", selectedProduct);
            startActivity(intent);
        });


    }


}