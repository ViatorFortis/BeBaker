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

//    private final GridItemClickListener mGridItemClickListener;
//
//    public interface GridItemClickListener {
//        void onGridItemClick(int adapterPosition);
//    }

    public RecipeAdapter(ArrayList<Recipe> recipeList/*, GridItemClickListener gridItemClickListener*/) {
        mRecipeList = recipeList;
        //mGridItemClickListener = gridItemClickListener;
    }

    public void addRecipes(ArrayList<Recipe> recipeList) {
        mRecipeList.addAll(recipeList);
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;

        private RecipeViewHolder(View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.rv_recipe_list);
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

