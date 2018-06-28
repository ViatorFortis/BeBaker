package com.viatorfortis.bebaker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Recipe;
import com.viatorfortis.bebaker.rv.RecipeAdapter;

public class MainActivity extends AppCompatActivity
        implements RecipeAdapter.OnRecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Toast.makeText(this, "Recipe of " + recipe.getName() + " clicked", Toast.LENGTH_LONG).show();
    }
}
