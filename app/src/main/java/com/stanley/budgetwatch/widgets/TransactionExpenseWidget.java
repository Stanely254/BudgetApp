package com.stanley.budgetwatch.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.stanley.budgetwatch.R;
import com.stanley.budgetwatch.activities.MainActivity;
import com.stanley.budgetwatch.activities.TransactionViewActivity;
import com.stanley.budgetwatch.db.DBHelper;

public class TransactionExpenseWidget extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        ComponentName widget = new ComponentName(context, TransactionExpenseWidget.class);
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent = new Intent(context, TransactionViewActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("type", DBHelper.TransactionDbIds.EXPENSE);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteView.setOnClickPendingIntent(R.id.addTransaction, pendingIntent);

        appWidgetManager.updateAppWidget(widget, remoteView);
        linkButtons(context, remoteView);
    }

    protected boolean hasInstances(final Context context){
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final int[] mAppWidgets = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        return mAppWidgets.length > 0;
    }

    protected void pushUpdate(final Context context, final int[] appWidgetIds, final RemoteViews views) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (appWidgetIds != null) {
            appWidgetManager.updateAppWidget(appWidgetIds, views);
        } else {
            appWidgetManager.updateAppWidget(new ComponentName(context, getClass()), views);
        }
    }

    protected PendingIntent buildPendingIntent(Context context, final String action, final ComponentName serviceName) {
        Intent intent = new Intent(action);
        intent.setComponent(serviceName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return PendingIntent.getForegroundService(context, 0, intent, 0);
        } else {
            return PendingIntent.getService(context, 0, intent, 0);
        }
    }

    private void linkButtons(final Context context, final RemoteViews remoteViews){
        Intent action;
        PendingIntent pendingIntent;

        final ComponentName serviceName = new ComponentName(context, MainActivity.class);

        // Home
        action = new Intent(context, MainActivity.class);
        action.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(context, 0, action, 0);
        remoteViews.setOnClickPendingIntent(R.id.imageView, pendingIntent);
    }
}
