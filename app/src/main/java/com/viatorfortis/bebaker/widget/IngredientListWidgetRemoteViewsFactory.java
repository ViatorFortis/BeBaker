package com.viatorfortis.bebaker.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viatorfortis.bebaker.R;
import com.viatorfortis.bebaker.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

class IngredientListWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final ArrayList<Ingredient> mIngredientList;
    private final int VIEW_TYPE_COUNT = 1;

    public IngredientListWidgetRemoteViewsFactory(Context context) {
        mContext = context;
        mIngredientList = new ArrayList<Ingredient>();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        final String WIDGET_RECIPE_NAME_PREF_KEY = mContext.getString(R.string.widget_recipe_name_pref_key);

//        if (sharedPreferences.contains(WIDGET_RECIPE_NAME_PREF_KEY) ) {
//            String recipeName = sharedPreferences.getString(WIDGET_RECIPE_NAME_PREF_KEY, "");
//        }

        final String WIDGET_INGREDIENT_LIST_PREF_KEY = mContext.getString(R.string.widget_ingredient_list_pref_key);

        if (sharedPreferences.contains(WIDGET_INGREDIENT_LIST_PREF_KEY) ) {
            String ingredientListJson = sharedPreferences.getString(WIDGET_INGREDIENT_LIST_PREF_KEY, null);

            mIngredientList.clear();

            if (ingredientListJson != null) {
                Gson gson = new Gson();

                TypeToken<ArrayList<Ingredient>> token = new TypeToken<ArrayList<Ingredient>>() {};
                List<Ingredient> newIngredientList = gson.fromJson(ingredientListJson, token.getType() );
                mIngredientList.addAll(newIngredientList);
            }
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredientList == null
                || mIngredientList.size() == 0) {
            return null;
        }

        Ingredient ingredient = mIngredientList.get(position);

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_list_item);

        remoteViews.setTextViewText(R.id.widget_ingredient, ingredient.getIngredient() );
        remoteViews.setTextViewText(R.id.widget_measure, ingredient.getMeasure() );
        remoteViews.setTextViewText(R.id.widget_quantity, String.valueOf(ingredient.getQuantity() ) );

        return remoteViews;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
