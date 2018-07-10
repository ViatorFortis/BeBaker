package com.viatorfortis.bebaker.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import java.util.ArrayList;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Ingredient;
import com.viatorfortis.bebaker.rv.IngredientAdapter;


public class IngredientListFragment extends Fragment {

    private ArrayList<Ingredient> mIngredientList;

    public IngredientListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_ingredient_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext() );
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        IngredientAdapter ingredientAdapter = new IngredientAdapter(mIngredientList);
        recyclerView.setAdapter(ingredientAdapter);

        return rootView;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
    }
}
