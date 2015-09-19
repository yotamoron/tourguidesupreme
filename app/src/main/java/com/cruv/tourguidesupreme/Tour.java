package com.cruv.tourguidesupreme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cruv.tourguidesupreme.bean.DbBean;
import com.cruv.tourguidesupreme.bean.GuideBean;
import com.cruv.tourguidesupreme.bean.LocationBean;
import com.cruv.tourguidesupreme.bean.TourBean;
import com.cruv.tourguidesupreme.db.DB;
import com.cruv.tourguidesupreme.db.DbUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Tour extends AppCompatActivity {
    public static final String TOUR_ID = "com.cruv.tourguidesupreme.Tour.TOUR_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context me = this;
        setContentView(R.layout.activity_tour);
        final Intent intent = getIntent();
        final String byTourId = intent.getStringExtra(TOUR_ID);
        final DbBean dbBean = DB.getDB(getResources());
        final TourBean tourBean = DbUtils.byTourId(dbBean, Integer.parseInt(byTourId));
        final TextView tourNameTextView = (TextView) findViewById(R.id.activity_tour_tour_name);
        tourNameTextView.setText(tourBean.getTourName());

        final TextView guideNameTextView = (TextView) findViewById(R.id.activity_tour_guide_name);
        final GuideBean guideBean = DbUtils.byGuideId(dbBean, tourBean.getGuideId());
        guideNameTextView.setText(guideBean.getGuideName());

        final TextView guideIdTextView = (TextView) findViewById(R.id.activity_tour_guide_id);
        guideIdTextView.setText(String.valueOf(guideBean.getGuideId()));

        populateLocations(tourBean.getTourId(), dbBean, me);
        guideNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView guideIdTextView = (TextView) findViewById(R.id.activity_tour_guide_id);
                final Intent intent = new Intent(me, Guide.class);
                intent.putExtra(Guide.GUIDE_ID, guideIdTextView.getText());
                startActivity(intent);
            }
        });
    }

    private void populateLocations(final int tourId, final DbBean dbBean, final Context me) {
        final List<LocationBean> locations = DbUtils.allLocationsByTourId(dbBean, tourId);
        final List<Map<String, String>> data = getLocationsData(locations);

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,R.layout.location_list_item,
                new String[]{"location_list_item_location_name", "location_list_item_location_id"},
                new int[]{R.id.location_list_item_location_name, R.id.location_list_item_location_id});

        final ListView listView = (ListView) findViewById(R.id.activity_tour_location_list);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView textView = (TextView) view.findViewById(R.id.location_list_item_location_id);
                final String locationId = textView.getText().toString();
                final Intent intent = new Intent(me, Location.class);
                intent.putExtra(Location.LOCATION_ID, locationId);
                startActivity(intent);
            }
        });
    }

    private List<Map<String, String>> getLocationsData(List<LocationBean> locations) {
        final List<Map<String, String>> data = new LinkedList<>();

        for (final LocationBean locationBean : locations) {
            final Map<String, String> datum = new HashMap<>();

            datum.put("location_list_item_location_name", locationBean.getLocationName());
            datum.put("location_list_item_location_id", String.valueOf(locationBean.getLocationId()));
            data.add(datum);
        }

        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tour, menu);
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
