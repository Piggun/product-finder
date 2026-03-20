package com.example.productfinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
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

    String[] sortByOptions = {"Description", "Category", "Price", "ID"};
    public static String[] categories = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    public static List<Product> productList = new ArrayList<>();

    ProductAdapter adapter;


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

//        List<Product> productList = new ArrayList<>();

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

        adapter = new ProductAdapter(this, productList);

        productListView.setAdapter(adapter);


        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item_sort, sortByOptions);
        autoCompleteTextView.setAdapter(adapterItems);

        // Sort By functionality
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();

                // Sort the list based on the selected item
                applyCurrentSort(item);

                Toast.makeText(MainActivity.this, "Sorted by: " + item, Toast.LENGTH_SHORT).show();

                // Update the list view with the sorted data by resetting the filter
                adapter.getFilter().filter(searchBar.getQuery());
            }
        });

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
            Product selectedProduct = adapter.getItem(position);

            Toast.makeText(MainActivity.this, "Selected product: " + selectedProduct.getDescription(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProductDetailsActivity.class);
            intent.putExtra("product", selectedProduct);
            startActivity(intent);
        });


        View sortByLayout = findViewById(R.id.SortByLayout);

        // Make the SearchBar expand to full width when focused
        searchBar.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            sortByLayout.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // This runs every time you come back to this activity
        // Updates the list view with the new data
        if (adapter != null) {
            // Update list with current filter selected
            applyCurrentSort(autoCompleteTextView.getText().toString());
            adapter.getFilter().filter("");
        }
    }

    private void applyCurrentSort(String item) {
        if (item == null || item.isEmpty()) return;

        switch (item) {
            case "Description":
                adapter.sort(Comparator.comparing(Product::getDescription));
                break;
            case "Category":
                adapter.sort(Comparator.comparing(Product::getCategory));
                break;
            case "Price":
                adapter.sort(Comparator.comparing(Product::getPrice));
                break;
            case "ID":
                adapter.sort(Comparator.comparing(Product::getId));
                break;
        }
    }

}