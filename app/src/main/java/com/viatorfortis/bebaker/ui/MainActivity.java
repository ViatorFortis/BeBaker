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
import com.viatorfortis.bebaker.utilities.JsonUtils;
import com.viatorfortis.bebaker.utilities.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks {

    private static final int RECIPE_LIST_LOADER_ID = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = getSupportLoaderManager();
        ArrayList<Recipe> recipeList;

        if (savedInstanceState == null
                || !savedInstanceState.containsKey(getString(R.string.recipe_list_key) ) ) {
            loaderManager.restartLoader(RECIPE_LIST_LOADER_ID, new Bundle(), this).forceLoad();
        }
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<List<Recipe>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }

            @Override
            public List<Recipe> loadInBackground() {
                String json;
                try {
                    json = NetworkUtils.getRecipeListJson();
                    if (!json.isEmpty()) {
                        return JsonUtils.parseRecipeListJson(json);
                    }
                } catch (IOException e) {
                    Log.d(e.getClass().getName(), e.getMessage());
                } catch (JsonSyntaxException e) {
                    Log.d(e.getClass().getName(), e.getMessage());
                }

                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object object) {
        ArrayList<Recipe> recipeList;

        if (object != null
                && object instanceof ArrayList) {
            recipeList = (ArrayList<Recipe>) object;

            if (recipeList.size() > 0) {
                // add items to RV adapter

            } else {
                Toast.makeText(this, getString(R.string.recipe_loading_warning_toast), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.recipe_loading_error_toast), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }
}
