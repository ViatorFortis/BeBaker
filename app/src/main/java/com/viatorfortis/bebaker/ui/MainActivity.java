package com.viatorfortis.bebaker.ui;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Recipe;
import com.viatorfortis.bebaker.rv.RecipeAdapter;
import com.viatorfortis.bebaker.utilities.JsonUtils;
import com.viatorfortis.bebaker.utilities.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LoaderManager loaderManager = getSupportLoaderManager();
//        ArrayList<Recipe> recipeList;

//        if (savedInstanceState == null
//                || !savedInstanceState.containsKey(getString(R.string.recipe_list_key) ) ) {
//            loaderManager.restartLoader(RECIPE_LIST_LOADER_ID, new Bundle(), this).forceLoad();
//        }
    }


}
