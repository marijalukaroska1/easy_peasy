package com.example.easypeasy.screens.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.easypeasy.R;
import com.example.easypeasy.models.RecipeDetails;
import com.example.easypeasy.screens.recipeInformation.RecipeInformationActivity;

import java.util.List;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeTitleTextView, noRecipesFoundTextView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitleTextView = itemView.findViewById(R.id.recipeText);
            imageView = itemView.findViewById(R.id.recipeImage);
            noRecipesFoundTextView = itemView.findViewById(R.id.noRecipesFoundId);
        }
    }

    private static final String TAG = "RecipesAdapter";
    private final List<RecipeDetails> recipesList;
    private final Context context;

    public RecipesListAdapter(List<RecipeDetails> recipesList, Context context) {
        Log.d(TAG, "recipesList: " + recipesList.size());
        this.recipesList = recipesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            Log.d(TAG, "Clicked on recipe: " + recipesList.get(position).getTitle() + " id: " + recipesList.get(position).getId());
            Intent intent = new Intent(context, RecipeInformationActivity.class);
            intent.putExtra("recipeId", recipesList.get(position).getId());
            context.startActivity(intent);
        });
        holder.recipeTitleTextView.setText(recipesList.get(position).getTitle());
        Glide.with(context)
                .load(recipesList.get(position).getImageUrl())
                .error(R.mipmap.ic_launcher)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) // resize does not respect aspect ratio
                .fitCenter() // scale to fit entire image within ImageView
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e(TAG, "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }
}
