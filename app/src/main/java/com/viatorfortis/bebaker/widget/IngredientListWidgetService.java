package com.viatorfortis.bebaker.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class IngredientListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new IngredientListWidgetRemoteViewsFactory(this.getApplicationContext() ) );
    }
}
