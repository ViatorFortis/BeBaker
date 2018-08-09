package com.viatorfortis.bebaker.rv;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Step;

public class RecipeDetailAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int INGREDIENT_VIEW_TYPE_ID = 0;
    private final int STEP_VIEW_TYPE_ID = 1;

    private int mCurrentStepNumber;

    private final ArrayList<Step> mStepList;

    private RecyclerView mRecyclerView;

    private final boolean mHighlightSelectedStepViewholder;

    private final OnIngredientListClickListener mIngredientListCallback;

    public interface OnIngredientListClickListener {
        void onIngredientListClick();
    }

    private final OnStepClickListener mStepCallback;

    public interface OnStepClickListener {
        void onStepClick(int stepId);
    }

    public RecipeDetailAdapter(ArrayList<Step> stepList, Context context, boolean highlightSelectedViewholder, int currentStepNumber) {
        mStepList = stepList;
        mHighlightSelectedStepViewholder = highlightSelectedViewholder;
        mIngredientListCallback = (OnIngredientListClickListener) context;
        mStepCallback = (OnStepClickListener) context;
        mCurrentStepNumber = currentStepNumber;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    public ArrayList<Step> getStepList() {
        return mStepList;
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

    public class IngredientListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public IngredientListViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mIngredientListCallback.onIngredientListClick();
        }
    }

    class StepViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView mShortDescriptionTextView;

        StepViewHolder(View itemView) {
            super(itemView);

            mShortDescriptionTextView = itemView.findViewById(R.id.tv_step_short_description);

            itemView.setOnClickListener(this);
        }

        void populate(Step step) {
            mShortDescriptionTextView.setText(step.getShortDescription() );
        }

        @Override
        public void onClick(View v) {

            if (mHighlightSelectedStepViewholder) {
                View view = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentStepNumber);
                deemphasizeViewHolder(view);

                mCurrentStepNumber = getAdapterPosition();

                view = mRecyclerView.getLayoutManager().findViewByPosition(mCurrentStepNumber);
                highlightViewHolder(view);
            }

            mStepCallback.onStepClick(getAdapterPosition() - 1);
        }

        private void deemphasizeViewHolder(View view) {
            view.setBackgroundResource(R.color.cardview_light_background);
            TextView textView = view.findViewById(R.id.tv_step_short_description);
            textView.setTextColor(0x7F040028);
        }

        private void highlightViewHolder(View view) {
            view.setBackgroundResource(R.color.colorPrimaryDark);
            TextView textView = view.findViewById(R.id.tv_step_short_description);
            textView.setTextColor(Color.WHITE);
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
        viewHolder = new IngredientListViewHolder(itemView);
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

        if (mHighlightSelectedStepViewholder) {
            if (position == mCurrentStepNumber) {
                View view = viewholder.itemView;

                view.setBackgroundResource(R.color.colorPrimaryDark);
                TextView textView = view.findViewById(R.id.tv_step_short_description);
                textView.setTextColor(Color.WHITE);
            }
        }
    }

    public int getCurrentStepNumber() {
        return mCurrentStepNumber;
    }
}
