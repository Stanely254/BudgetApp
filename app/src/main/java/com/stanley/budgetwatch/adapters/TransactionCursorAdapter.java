package com.stanley.budgetwatch.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stanley.budgetwatch.R;
import com.stanley.budgetwatch.models.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TransactionCursorAdapter extends CursorAdapter {

    public TransactionCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    private final DateFormat DATE_FORMATTER = SimpleDateFormat.getDateInstance();

    public class ViewHolder {
        TextView nameField;
        TextView valueField;
        TextView dateField;
        TextView budgetField;
        ImageView receiptIcon;
        TextView note;
        View noteLayout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_layout, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.nameField = (TextView) view.findViewById(R.id.name);
        holder.valueField = (TextView) view.findViewById(R.id.value);
        holder.dateField = (TextView) view.findViewById(R.id.date);
        holder.budgetField = (TextView) view.findViewById(R.id.budget);
        holder.receiptIcon = (ImageView) view.findViewById(R.id.receiptIcon);
        holder.note = (TextView) view.findViewById(R.id.note);
        holder.noteLayout = view.findViewById(R.id.noteLayout);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();

        // Extract properties from cursor
        Transaction transaction = Transaction.toTransaction(cursor);

        // Populate fields with extracted properties
        holder.nameField.setText(transaction.description);
        holder.valueField.setText(String.format(Locale.US, "%.2f", transaction.value));
        holder.budgetField.setText(transaction.budget);

        holder.dateField.setText(DATE_FORMATTER.format(transaction.dateMs));

        if(transaction.receipt.isEmpty()) {
            holder.receiptIcon.setVisibility(View.GONE);
        } else {
            holder.receiptIcon.setVisibility(View.VISIBLE);
        }

        if(transaction.note.isEmpty()) {
            holder.noteLayout.setVisibility(View.GONE);
            holder.note.setText("");
        } else {
            holder.noteLayout.setVisibility(View.VISIBLE);
            holder.note.setText(transaction.note);
        }
    }
}
