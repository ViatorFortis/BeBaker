package com.viatorfortis.bebaker.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Recipe;
import java.util.ArrayList;

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final ArrayList<Recipe> mRecipeList;

    private final OnRecipeClickListener mCallback;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(ArrayList<Recipe> recipeList, OnRecipeClickListener callback) {
        mRecipeList = recipeList;
        mCallback = callback;
    }

    public void addRecipes(ArrayList<Recipe> recipeList) {
        mRecipeList.addAll(recipeList);
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView mNameTextView;

        private RecipeViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void onClick(View view) {
            mCallback.onRecipeClick(mRecipeList.get(getAdapterPosition() ) );
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext() );
        View view = layoutInflater.inflate(R.layout.viewholder_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.mNameTextView.setText(mRecipeList.get(position).getName() );
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public ArrayList<Recipe> getRecipeList() {
        return mRecipeList;
    }
}

