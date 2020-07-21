package com.stanley.budgetwatch.db;

import android.content.Context;
import android.nfc.FormatException;

import com.stanley.budgetwatch.utils.ImportExportProgressUpdater;

import java.io.IOException;
import java.io.InputStream;

public interface DatabaseImporter {
    /**
     * Import data from the input stream in a given format into
     * the database.
     * @throws IOException
     * @throws FormatException
     */
    void importData(Context context, DBHelper db, InputStream input,
                    ImportExportProgressUpdater updater) throws IOException, FormatException, InterruptedException, com.stanley.budgetwatch.exception.FormatException;
}
