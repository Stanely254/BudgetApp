package com.stanley.budgetwatch.activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.stanley.budgetwatch.R;
import com.stanley.budgetwatch.adapters.TransactionPagerAdapter;
import com.stanley.budgetwatch.db.DBHelper;
import com.stanley.budgetwatch.db.DatabaseCleanupTask;
import com.stanley.budgetwatch.db.TransactionDatabaseChangedReceiver;
import com.stanley.budgetwatch.utils.CalendarUtil;

public class TransactionActivity extends AppCompatActivity {
    private TransactionDatabaseChangedReceiver _dbChanged;
    private static final String TAG = "BudgetWatch";

    private boolean _currentlySearching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        _dbChanged = new TransactionDatabaseChangedReceiver();
        this.registerReceiver(_dbChanged, new IntentFilter(TransactionDatabaseChangedReceiver.ACTION_DATABASE_CHANGED));

        String search = getIntent().getStringExtra(SearchManager.QUERY);

        resetView(search);
    }

    private void resetView(String search){
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText(R.string.expensesTitle));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.revenuesTitle));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new TransactionPagerAdapter
                (getSupportFragmentManager(), search, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        if(_dbChanged.isHasChanged() || Intent.ACTION_SEARCH.equals(getIntent().getAction())){
            String search = null;
            if(_currentlySearching){
                search = getIntent().getStringExtra(SearchManager.QUERY);
            }
            resetView(search);
            _dbChanged.reset();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.transaction_menu, menu);

        //Associate searchable  configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                _currentlySearching = false;

                //Re-populate the transaction
                onResume();

                //false: allow the default cleanup behavior on the search view on closing
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _currentlySearching = true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.action_add:
                Intent i =  new Intent(getApplicationContext(), TransactionViewActivity.class);
                final Bundle bundle = new Bundle();
                bundle.putInt("type", getCurrentTabType());
                i.putExtras(bundle);
                startActivity(i);
                return true;

            case R.id.action_purge_receipts:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.cleanupHelp);

                final View view = getLayoutInflater().inflate(R.layout.cleanup_layout, null, false);

                builder.setView(view);
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton(R.string.clean, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatePicker endDatePicker = (DatePicker) view.findViewById(R.id.endDate);

                        long endOfBudgetMs = CalendarUtil.getEndOfDayMs(endDatePicker.getYear(),
                                endDatePicker.getMonth(), endDatePicker.getDayOfMonth());

                        DatabaseCleanupTask task = new DatabaseCleanupTask(TransactionActivity.this,
                                endOfBudgetMs);
                        task.execute();
                    }
                }).show();
                return true;

            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private int getCurrentTabType(){
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        if(tabLayout.getSelectedTabPosition() == 0){
            return DBHelper.TransactionDbIds.EXPENSE;
        } else {
            return DBHelper.TransactionDbIds.REVENUE;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(TAG, "Received search: " + query);

            setIntent(intent);
            // onResume() will be called right after this, so the search will be used
        }
    }

    @Override
    public void onDestroy(){
        this.unregisterReceiver(_dbChanged);
        super.onDestroy();
    }
}
