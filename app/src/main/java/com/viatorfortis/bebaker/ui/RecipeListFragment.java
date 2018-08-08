package com.viatorfortis.bebaker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RecipeListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks {

    private static final int RECIPE_LIST_LOADER_ID = 13;


    private RecipeAdapter.OnRecipeClickListener mActivity;


    private RecipeAdapter mRecipeAdapter;

    public RecipeListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mActivity = (RecipeAdapter.OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement RecipeAdapter.OnRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.rv_recipe_list);

        GridLayoutManager gridLayoutManager= new GridLayoutManager (getContext(), 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        mRecipeAdapter = new RecipeAdapter(recipeList, mActivity);
        recyclerView.setAdapter(mRecipeAdapter);

        final String RECIPE_LIST_PARCEL_KEY = getString(R.string.recipe_list_parcel_key);

        if (savedInstanceState == null
                || !savedInstanceState.containsKey(RECIPE_LIST_PARCEL_KEY) ) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(RECIPE_LIST_LOADER_ID, new Bundle(), this).forceLoad();
        } else {
            ArrayList<Recipe> savedRecipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST_PARCEL_KEY);
            mRecipeAdapter.addRecipes(savedRecipeList);
        }

        return rootView;
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<List<Recipe>>(getContext() ) {

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
    public void onLoadFinished(@NonNull Loader loader, Object object) {
        ArrayList<Recipe> recipeList;

        if (object != null
                && object instanceof ArrayList) {
            recipeList = (ArrayList<Recipe>) object;

            if (recipeList.size() > 0) {
                mRecipeAdapter.addRecipes(recipeList);
            } else {
                Toast.makeText(getContext(), getString(R.string.recipe_loading_warning_toast), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.recipe_loading_error_toast), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(getString(R.string.recipe_list_parcel_key), mRecipeAdapter.getRecipeList() );
    }
}
