package com.cruv.tourguidesupreme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cruv.tourguidesupreme.bean.DbBean;
import com.cruv.tourguidesupreme.bean.GuideBean;
import com.cruv.tourguidesupreme.bean.TourBean;
import com.cruv.tourguidesupreme.db.DB;
import com.cruv.tourguidesupreme.db.DbUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SearchTourResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SearchTourResults me = this;
        setContentView(R.layout.activity_search_tour_results);

        final String byTour = getIntent().getStringExtra(MainActivity.SEARCH_A_TOUR).toLowerCase();
        final DbBean dbBean = DB.getDB(getResources());
        final List<Map<String, String>> data = getToursData(byTour, dbBean);

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,R.layout.tour_list_item,
                new String[]{"tour_name", "tour_id", "guide_name_per_tour", "guide_id_per_tour"},
                new int[]{R.id.tour_name, R.id.tour_id, R.id.guide_name_per_tour, R.id.guide_id_per_tour});

        final ListView listView = (ListView) findViewById(R.id.tour_list);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView textView = (TextView) view.findViewById(R.id.tour_id);
                final String tourId = textView.getText().toString();
                final Intent intent = new Intent(me, Tour.class);
                intent.putExtra(Tour.TOUR_ID, tourId);
                startActivity(intent);
            }
        });
    }

    @NonNull
    private List<Map<String, String>> getToursData(String byTour, DbBean dbBean) {
        final List<Map<String, String>> data = new LinkedList<>();

        for (final TourBean tourBean : dbBean.getTours()) {
            if (tourBean.getTourName().toLowerCase().contains(byTour)) {
                final Map<String, String> tourData = new HashMap<>();
                final GuideBean guideBean = DbUtils.byGuideId(dbBean, tourBean.getGuideId());

                tourData.put("tour_name", tourBean.getTourName());
                tourData.put("tour_id", String.valueOf(tourBean.getTourId()));
                tourData.put("guide_per_tour", guideBean.getGuideName());
                tourData.put("guide_id_per_tour", String.valueOf(guideBean.getGuideId()));

                data.add(tourData);
            }
        }
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_tour_results, menu);
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
}
