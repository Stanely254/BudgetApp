package com.stanley.budgetwatch.utils;

import android.content.Context;
import android.util.Log;

import com.stanley.budgetwatch.db.DBHelper;
import com.stanley.budgetwatch.db.DatabaseExporter;
import com.stanley.budgetwatch.db.JsonDatabaseExporter;
import com.stanley.budgetwatch.db.ZipDatabaseExporter;

import java.io.IOException;
import java.io.OutputStream;

public class MultiFormatExporter {
    private static final String TAG = "BudgetWatch";

    /**
     * Attempts to export data to the output stream in the
     * given format, if possible.
     *
     * The output stream is closed on success.
     *
     * @return true if the database was successfully exported,
     * false otherwise. If false, partial data may have been
     * written to the output stream, and it should be discarded.
     */
    public static boolean exportData(Context context, DBHelper db, Long startTimeMs, Long endTimeMs, OutputStream output, DataFormat format, ImportExportProgressUpdater updater) {
        DatabaseExporter exporter = null;

        switch(format) {
            case CSV:
                exporter = new CsvDatabaseExporter();
                break;
            case JSON:
                exporter = new JsonDatabaseExporter();
                break;
            case ZIP:
                exporter = new ZipDatabaseExporter();
                break;
        }

        if(exporter != null) {
            try {
                exporter.exportData(context, db, startTimeMs, endTimeMs, output, updater);
                return true;
            } catch(IOException | InterruptedException e) {
                Log.e(TAG, "Failed to export data", e);
            }

            return false;
        } else {
            Log.e(TAG, "Unsupported data format exported: " + format.name());
            return false;
        }
    }
}
