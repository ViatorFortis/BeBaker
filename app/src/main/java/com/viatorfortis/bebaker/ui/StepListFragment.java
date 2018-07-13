package com.viatorfortis.bebaker.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Recipe;
import com.viatorfortis.bebaker.model.Step;
import com.viatorfortis.bebaker.rv.RecipeDetailAdapter;

import java.util.ArrayList;

public class StepListFragment extends Fragment {

    private Recipe mRecipe;

    //private RecipeDetailAdapter.OnIngredientListClickListener mActivity;

    private Context mContext;

    private RecipeDetailAdapter mRecipeDetailAdapter;

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    public StepListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            //mActivity = (RecipeDetailAdapter.OnIngredientListClickListener) context;
            RecipeDetailAdapter.OnIngredientListClickListener ingredientListCallBack = (RecipeDetailAdapter.OnIngredientListClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement RecipeDetailAdapter.OnIngredientListClickListener");
        }

        try {
            RecipeDetailAdapter.OnStepClickListener stepCallBack = (RecipeDetailAdapter.OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement RecipeDetailAdapter.OnIngredientListClickListener");
        }

        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        //( (TextView) rootView.findViewById(R.id.tv_recipe_name) ).setText(mRecipe.getName() );
        RecyclerView recyclerView = rootView.findViewById(R.id.rv_step_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext() );
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        final String STEP_LIST_PARCEL_KEY = getString(R.string.step_list_parcel_key);

        ArrayList<Step> stepList;

        if (savedInstanceState == null
                || !savedInstanceState.containsKey(STEP_LIST_PARCEL_KEY) ) {
            stepList = mRecipe.getStepList();
        } else {
            ArrayList<Step> savedStepList = savedInstanceState.getParcelableArrayList(STEP_LIST_PARCEL_KEY);
            stepList = savedStepList;
        }

        mRecipeDetailAdapter = new RecipeDetailAdapter(stepList, mContext);
        recyclerView.setAdapter(mRecipeDetailAdapter);

        //recipeDetailAdapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.step_list_parcel_key), mRecipeDetailAdapter.getStepList() );
    }
}
