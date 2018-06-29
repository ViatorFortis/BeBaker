package com.viatorfortis.bebaker.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Recipe;

public class StepListFragment extends Fragment {

    private Recipe mRecipe;

    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    public StepListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        ( (TextView) rootView.findViewById(R.id.tv_recipe_name) ).setText(mRecipe.getName() );

        return rootView;
    }
}
