package com.viatorfortis.bebaker.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Step;

import java.util.ArrayList;

public class StepDetailsActivity extends AppCompatActivity {

    private ArrayList<Step> mStepList;

    private int mStepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Toolbar appBar = findViewById(R.id.tb_step_details);
        setSupportActionBar(appBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Bundle extraBundle = getIntent().getExtras();
        final String recipeName = extraBundle.getString(getString(R.string.recipe_name_key), getString(R.string.no_name_recipe));
        setTitle(recipeName);

        try {
            mStepList = getIntent().getParcelableArrayListExtra(getString(R.string.step_list_parcel_key) );
        } catch (NullPointerException e) {
            Toast.makeText(this, getString(R.string.get_step_list_parcel_npe), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        mStepId = getIntent().getExtras().getInt(getString(R.string.step_id_key) );

        if (savedInstanceState == null) {
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStep(mStepList, mStepId, true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepDetailsFragment)
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
