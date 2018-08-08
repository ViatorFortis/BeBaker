package com.viatorfortis.bebaker.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Ingredient> mIngredientList;

    public IngredientAdapter (List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        private TextView mIngredientTextView;
        private TextView mQuantityTextView;
        private TextView mMeasureTextView;

        IngredientViewHolder(View itemView) {
            super(itemView);

            mIngredientTextView = itemView.findViewById(R.id.tv_ingredient);
            mQuantityTextView = itemView.findViewById(R.id.tv_quantity);
            mMeasureTextView = itemView.findViewById(R.id.tv_measure);
        }

        void populate(Ingredient ingredient) {
            mIngredientTextView.setText(ingredient.getIngredient() );
            mQuantityTextView.setText(String.valueOf(ingredient.getQuantity() ) );
            mMeasureTextView.setText(ingredient.getMeasure() );
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext() );
        View view = layoutInflater.inflate(R.layout.viewholder_ingredient, parent, false);

        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ( (IngredientViewHolder) holder).populate(mIngredientList.get(position) );
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }
}
