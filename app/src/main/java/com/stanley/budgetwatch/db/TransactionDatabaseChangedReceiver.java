package com.stanley.budgetwatch.db;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TransactionDatabaseChangedReceiver extends BroadcastReceiver {
    public static final String ACTION_DATABASE_CHANGED = "com.stanley.budgetwatch.db.TRANSACTION_DATABASE_CHANGED";

    private boolean hasChanged = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        hasChanged = true;
    }

    public boolean isHasChanged(){
        return hasChanged;
    }

    public void reset(){
        hasChanged = false;
    }
}
