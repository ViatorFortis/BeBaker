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

    private ArrayList<Recipe> mRecipeList;

    private final RecipeClickListener mRecipeClickListener;

    public interface RecipeClickListener {
        void onRecipeClick(int adapterPosition);
    }

    public Recipe getRecipe(int adapterPosition) {
        return mRecipeList.get(adapterPosition);
    }

    public RecipeAdapter(ArrayList<Recipe> recipeList, RecipeClickListener recipeClickListener) {
        mRecipeList = recipeList;
        mRecipeClickListener = recipeClickListener;
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

            mNameTextView = itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void onClick(View view) {
            mRecipeClickListener.onRecipeClick(getAdapterPosition() );
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
}

