package com.cruv.tourguidesupreme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.cruv.tourguidesupreme.bean.DbBean;
import com.cruv.tourguidesupreme.db.DB;
import com.cruv.tourguidesupreme.db.DbUtils;

public class MainActivity extends AppCompatActivity {
    public static final String SEARCH_A_TOUR = "com.cruv.tourguidesupreme.MainActivity.SEARCH_A_TOUR";
    public static final String SEARCH_A_GUIDE = "com.cruv.tourguidesupreme.MainActivity.SEARCH_A_GUIDE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context me = this;
        final DbBean dbBean = DB.getDB(getResources());

        final String[] tourNames = DbUtils.tourNames(dbBean);
        final AutoCompleteTextView tourTextView = (AutoCompleteTextView) findViewById(R.id.search_a_tour);
        tourTextView.setThreshold(1);
        final ArrayAdapter<String> tourAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, tourNames);
        tourTextView.setAdapter(tourAdapter);
        tourTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(me, SearchTourResults.class);
                final String tour = ((AppCompatTextView) view).getText().toString();
                intent.putExtra(SEARCH_A_TOUR, tour);
                startActivity(intent);
            }
        });

        final String[] guideNames = DbUtils.guideNames(dbBean);
        final AutoCompleteTextView guideTextView = (AutoCompleteTextView) findViewById(R.id.search_a_guide);
        guideTextView.setThreshold(1);
        final ArrayAdapter<String> guideAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, guideNames);
        guideTextView.setAdapter(guideAdapter);

        guideTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(me, SearchGuideResults.class);
                final String guide = ((AppCompatTextView)view).getText().toString();
                intent.putExtra(SEARCH_A_GUIDE, guide);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searchTour(final View view) {
        final Intent intent = new Intent(this, SearchTourResults.class);
        final EditText editText = (EditText) findViewById(R.id.search_a_tour);
        final String tour = editText.getText().toString();
        intent.putExtra(SEARCH_A_TOUR, tour);
        startActivity(intent);
    }

    public void searchGuide(final View view) {
        final Intent intent = new Intent(this, SearchGuideResults.class);
        final EditText editText = (EditText) findViewById(R.id.search_a_guide);
        final String guide = editText.getText().toString();
        intent.putExtra(SEARCH_A_GUIDE, guide);
        startActivity(intent);
    }
}
