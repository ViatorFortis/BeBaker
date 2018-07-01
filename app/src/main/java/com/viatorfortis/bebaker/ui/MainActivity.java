package com.viatorfortis.bebaker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Recipe;
import com.viatorfortis.bebaker.rv.RecipeAdapter;

public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.OnRecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar appBar = findViewById(R.id.tb_recipe_list);
        setSupportActionBar(appBar);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, StepListActivity.class);
        intent.putExtra(getString(R.string.recipe_parcel_key), recipe);
        startActivity(intent);
    }
}
