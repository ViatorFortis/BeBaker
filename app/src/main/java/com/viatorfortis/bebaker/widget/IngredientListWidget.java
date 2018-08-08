package com.viatorfortis.bebaker.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.viatorfortis.bebaker.R;

public class IngredientListWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_list);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String recipeName = sharedPreferences.getString(context.getString(R.string.widget_recipe_name_pref_key), "No recipe name saved.");

        remoteViews.setTextViewText(R.id.widget_tv_recipe_name, context.getString(R.string.ingredient_list_caption, recipeName) );

        Intent remoteServiceIntent = new Intent(context, IngredientListWidgetService.class);
        remoteServiceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, new int[] { appWidgetId } );

        remoteViews.setRemoteAdapter(R.id.widget_lv_ingredient_list, remoteServiceIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_tv_recipe_name);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_lv_ingredient_list);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

