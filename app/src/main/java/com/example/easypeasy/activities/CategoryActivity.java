package com.example.easypeasy.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.SearchCategoryRouter;
import com.example.easypeasy.adapters.CategoriesAdapter;
import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.events.CategoryItemClickListener;
import com.example.easypeasy.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends Activity {
    private List<Category> categories = new ArrayList<>();
    RecyclerView recyclerView;
    CategoriesAdapter categoriesAdapter;
    public SearchCategoryRouter router;
    int chosenCategoryPosition;
    Button continueButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configurator.INSTANCE.configure(this);
        setupUserInterface();
    }

    public void setupUserInterface() {
        setContentView(R.layout.activity_category);
        continueButton = findViewById(R.id.continueButtonId);
        continueButton.setOnClickListener(router);
        categories.add(new Category("ingredients", false));
        categories.add(new Category("nutrients", false));
        categories.add(new Category("cuisine", false));

        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        categoriesAdapter = new CategoriesAdapter(categories, router);
        recyclerView.setAdapter(categoriesAdapter);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
