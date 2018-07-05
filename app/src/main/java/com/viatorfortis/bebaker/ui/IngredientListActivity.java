package com.viatorfortis.bebaker.ui;

import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Ingredient;

public class IngredientListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ingredient_list);

        Toolbar appBar = findViewById(R.id.tb_ingredient_list);
        setSupportActionBar(appBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String recipeName;

        try {
            recipeName = getIntent().getExtras().getString(getString(R.string.recipe_name_key));
        } catch (NullPointerException e) {
            Toast.makeText(this, R.string.get_recipe_name_npe, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        ArrayList<Ingredient> ingredientList;

        try {
            ingredientList = getIntent().getParcelableArrayListExtra(getString(R.string.ingredient_list_parcel_key));
        } catch (NullPointerException e) {
            Toast.makeText(this, R.string.get_ingredient_list_parcel_npe, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (savedInstanceState == null) {
            setTitle(getString(R.string.ingredient_list_caption, recipeName) );

            IngredientListFragment ingredientListFragment = new IngredientListFragment();
            ingredientListFragment.setIngredientList(ingredientList);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.ingredient_list_container, ingredientListFragment)
                    .commit();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}
