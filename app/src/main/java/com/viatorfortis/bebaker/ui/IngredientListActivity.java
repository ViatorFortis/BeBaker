package com.viatorfortis.bebaker.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Ingredient;
import com.viatorfortis.bebaker.rv.IngredientAdapter;

import java.util.ArrayList;
import java.util.List;

public class IngredientListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Ingredient> ingredientList;

        try {
            ingredientList = getIntent().getParcelableArrayListExtra(getString(R.string.ingredient_list_parcel_key));
        } catch (NullPointerException e) {
            Toast.makeText(this, R.string.get_ingredient_list_parcel_npe, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_ingredient_list);

        RecyclerView recyclerView = findViewById(R.id.rv_ingredient_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredientList);
        recyclerView.setAdapter(ingredientAdapter);
    }
}
