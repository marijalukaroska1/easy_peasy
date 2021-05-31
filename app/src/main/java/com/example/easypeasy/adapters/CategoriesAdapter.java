package com.example.easypeasy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.events.CategoryItemClickListener;
import com.example.easypeasy.models.Category;
import com.google.android.material.button.MaterialButton;

import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private static final String TAG = CategoriesAdapter.class.getSimpleName();

    List<Category> categoriesList;
    private CategoryItemClickListener categoryItemClickListener;


    public CategoriesAdapter(List<Category> categoriesList, CategoryItemClickListener categoryItemClickListener) {
        this.categoriesList = categoriesList;
        this.categoryItemClickListener = categoryItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_categories_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.button.setOnClickListener(v -> {
            clearSelectedCategory();
            categoriesList.get(viewHolder.getAdapterPosition()).setSelected(true);
            notifyDataSetChanged();
        });

        return viewHolder;
    }

    private void clearSelectedCategory() {
        for (Category category : categoriesList) {
            category.setSelected(false);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.button.setText(categoriesList.get(position).getName());
        holder.button.setChecked(categoriesList.get(position).isSelected());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialButton button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonCategory);
        }
    }
}