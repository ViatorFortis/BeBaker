package com.viatorfortis.bebaker.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Ingredient;
import com.viatorfortis.bebaker.model.Recipe;
import com.viatorfortis.bebaker.model.Step;
import com.viatorfortis.bebaker.rv.RecipeDetailAdapter;
import java.util.ArrayList;

public class StepListActivity extends AppCompatActivity
        implements RecipeDetailAdapter.OnIngredientListClickListener, RecipeDetailAdapter.OnStepClickListener {

    private Recipe mRecipe;
    private boolean mTabletMode;

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

        if (savedInstanceState == null) {
            setTitle(mRecipe.getName() );

            mTabletMode = (findViewById(R.id.step_details_container) != null);

            StepListFragment stepListFragment = new StepListFragment();
            stepListFragment.setRecipe(mRecipe);
            stepListFragment.highlighCurrentStep(mTabletMode);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_list_container, stepListFragment)
                    .commit();

            if (mTabletMode) {
                StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
                stepDetailsFragment.setStep(mRecipe.getStepList(), 0, false);
                fragmentManager.beginTransaction()
                        .add(R.id.step_details_container, stepDetailsFragment)
                        .commit();
            }

        } else {
            final String RECIPE_NAME_KEY = getString(R.string.recipe_name_key);
            if (savedInstanceState.containsKey(RECIPE_NAME_KEY) ) {
                setTitle(savedInstanceState.getString(RECIPE_NAME_KEY) );
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onIngredientListClick() {
        Toast.makeText(this, "Ingredient list clicked", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, IngredientListActivity.class);
        intent.putExtra(getString(R.string.ingredient_list_parcel_key), (ArrayList<Ingredient>) mRecipe.getIngredientList() );
        intent.putExtra(getString(R.string.recipe_name_key), mRecipe.getName() );
        startActivity(intent);
    }

    @Override
    public void onStepClick(int stepId/*, View view*/) {

        if (mTabletMode) {

            //CardView cardView = view.findViewById(R.id.cv_step_list);

            /*
            ShapeDrawable redBorderDrawable = new ShapeDrawable();
            redBorderDrawable.setShape(new RectShape() );
            redBorderDrawable.getPaint().setColor(getResources().getColor(R.color.colorPrimaryDark) );
            redBorderDrawable.getPaint().setStrokeWidth(10f);
            redBorderDrawable.getPaint().setStyle(Paint.Style.STROKE);

            TextView textView = view.findViewById(R.id.tv_step_short_description);
            textView.setBackground(redBorderDrawable);
            */


            // USE THIS TO HIGHLIGHT CURRENT STEP
//            view.setBackgroundResource(R.color.colorPrimaryDark);
//            TextView textView = view.findViewById(R.id.tv_step_short_description);
//            textView.setTextColor(Color.WHITE);


            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStep(mRecipe.getStepList(), stepId, false);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()

                    .replace(R.id.step_details_container, stepDetailsFragment)

                    //.addToBackStack(null)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailsActivity.class);

            ArrayList<Step> stepList = (ArrayList<Step>) mRecipe.getStepList();

            intent.putParcelableArrayListExtra(getString(R.string.step_list_parcel_key), stepList);
            intent.putExtra(getString(R.string.step_id_key), stepId);

            startActivity(intent);

//        Intent intent = new Intent(this, StepDetailsActivity.class);
//
//        List<Step> stepList = mRecipe.getStepList();
//
//        Step step = stepList.get(stepId);
//        intent.putExtra(getString(R.string.selected_step_key), step);
//
//        Step prevStep = (stepId  == 0) ? null : stepList.get(stepId - 1);
//        intent.putExtra(getString(R.string.previous_step_key), prevStep);
//
//        Step nextStep = (stepId  == (stepList.size() - 1) ) ? null : stepList.get(stepId + 1);
//        intent.putExtra(getString(R.string.next_step_key), nextStep);
//
//        startActivity(intent);

//        String stepShortDescription = mRecipe.getStepList().get(stepId).getShortDescription();
//        Toast.makeText(this, stepShortDescription, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(getString(R.string.recipe_name_key), mRecipe.getName() );
    }
}
