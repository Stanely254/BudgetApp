package com.stanley.budgetwatch.db;

import android.content.Context;

import com.stanley.budgetwatch.utils.ImportExportProgressUpdater;

import java.io.IOException;
import java.io.OutputStream;

public interface DatabaseExporter {
    /**
     * Export the database to the output stream in a given format.
     * @throws IOException
     */
    void exportData(Context context, DBHelper db, Long startTimeMs, Long endTimeMs, OutputStream output,
                    ImportExportProgressUpdater updater) throws IOException, InterruptedException;
}
