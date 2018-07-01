package com.viatorfortis.bebaker.rv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Ingredient;
import com.viatorfortis.bebaker.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int INGREDIENT_VIEW_TYPE_ID = 0;
    private final int STEP_VIEW_TYPE_ID = 1;
    private Context mContext;

    private List<Step> mStepList;

    public RecipeDetailAdapter(List<Step> stepList, Context context) {
        mStepList = stepList;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mStepList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return INGREDIENT_VIEW_TYPE_ID;
        } else {
            return STEP_VIEW_TYPE_ID;
        }
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        public IngredientViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class StepViewHolder
            extends RecyclerView.ViewHolder {
        private TextView mShortDescriptionTextView;

        public StepViewHolder(View itemView) {
            super(itemView);

            mShortDescriptionTextView = itemView.findViewById(R.id.tv_step_short_description);
        }

        public void populate(Step step) {
            mShortDescriptionTextView.setText(step.getShortDescription() );
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        RecyclerView.ViewHolder viewHolder;

        if (viewType == INGREDIENT_VIEW_TYPE_ID) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_ingredient_list, parent, false);
        viewHolder = new IngredientViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext() )
                    .inflate(R.layout.viewholder_step, parent, false);
            viewHolder = new StepViewHolder(itemView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewholder, int position) {
        if(viewholder instanceof StepViewHolder) {
            ( (StepViewHolder) viewholder).populate(mStepList.get(position - 1) );
        }
    }
}
