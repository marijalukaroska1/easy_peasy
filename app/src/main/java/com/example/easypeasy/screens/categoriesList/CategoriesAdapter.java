package com.example.easypeasy.screens.categoriesList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.models.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> implements CategoryListViewItemMvc.Listener {

    private static final String TAG = CategoriesAdapter.class.getSimpleName();

    List<Category> mCategories;
    private final Listener mListener;
    LayoutInflater mInflater;

    public interface Listener {
        void onCategoryClicked(Category category);
    }

    public CategoriesAdapter(LayoutInflater inflater, Listener listener) {
        mListener = listener;
        mInflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CategoryListViewItemMvc viewItemMvc = new CategoryListViewItemMvcImpl(mInflater, parent);
        viewItemMvc.registerListener(this);
        return new ViewHolder(viewItemMvc);
    }

    public void updateUi(Category selectedCategory) {
        for (Category category : mCategories) {
            if (category == selectedCategory) {
                category.setSelected(true);
            } else {
                category.setSelected(false);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mViewItemMvc.bindCategory(mCategories.get(position));
    }

    public void bindCategories(List<Category> categoriesList) {
        mCategories = new ArrayList<>(categoriesList);
        mCategories.get(0).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public void clear() {
        mCategories.clear();
    }

    @Override
    public void onCategoryClicked(Category category) {
        mListener.onCategoryClicked(category);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CategoryListViewItemMvc mViewItemMvc;

        public ViewHolder(@NonNull CategoryListViewItemMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewItemMvc = viewMvc;
        }
    }
}