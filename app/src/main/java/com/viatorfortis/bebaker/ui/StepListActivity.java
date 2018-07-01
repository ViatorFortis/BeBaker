package com.viatorfortis.bebaker.ui;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Recipe;

public class StepListActivity extends AppCompatActivity {

    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Toolbar appBar = findViewById(R.id.tb_step_list);
        setSupportActionBar(appBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            mRecipe = getIntent().getParcelableExtra(getString(R.string.recipe_parcel_key));
        } catch (NullPointerException e) {
            Toast.makeText(this, R.string.get_recipe_parcel_npe, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if(savedInstanceState == null) {
            setTitle(mRecipe.getName() );

            StepListFragment stepListFragment = new StepListFragment();
            stepListFragment.setRecipe(mRecipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_list_container, stepListFragment)
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
