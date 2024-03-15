package com.example.jumann;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeleteItemActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button deleteButton;

    private List<Item> itemList;
    private List<Item> filteredList;
    private String query;

    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        searchEditText = findViewById(R.id.searchEditText);
        deleteButton = findViewById(R.id.deleteButton);
        recyclerView = findViewById(R.id.recyclerView);

        // Retrieve all items from DBHelperNews
        DBHelperNews dbHelper = new DBHelperNews(this);
        itemList = dbHelper.getAllItems();
        dbHelper.close();

        filteredList = new ArrayList<>(itemList);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                query = s.toString();
                filterItems(query);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteVisibleItems();
            }
        });

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }

    private void filterItems(String query) {
        filteredList.clear();

        if (query.isEmpty()) {
            filteredList.addAll(itemList);
        } else {
            for (Item item : itemList) {
                if (item.getTitle().toLowerCase().contains(query.toLowerCase()) ) {
                    filteredList.add(item);
                }
            }
        }

        // Notify the adapter of the data changes
        adapter.notifyDataSetChanged();
    }

    private void deleteVisibleItems() {
        Iterator<Item> iterator = filteredList.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                iterator.remove();
                DBHelperNews dbHelper = new DBHelperNews(this);
                dbHelper.deleteItem(item.getId());
                dbHelper.close();
            }
        }

        Toast.makeText(this, "Visible items deleted", Toast.LENGTH_SHORT).show();
        // Update the RecyclerView or refresh the data
        filterItems(query);
        finish();
    }
}
