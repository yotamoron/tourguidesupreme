package com.cruv.tourguidesupreme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.cruv.tourguidesupreme.bean.DbBean;
import com.cruv.tourguidesupreme.bean.GuideBean;
import com.cruv.tourguidesupreme.bean.TourBean;
import com.cruv.tourguidesupreme.db.DB;
import com.cruv.tourguidesupreme.db.DbUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Guide extends AppCompatActivity {
    public static final String GUIDE_ID = "com.cruv.tourguidesupreme.Guide.GUIDE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        final Context me = this;

        final Intent intent = getIntent();
        final String guideId = intent.getStringExtra(GUIDE_ID);
        final DbBean dbBean = DB.getDB(getResources());
        final GuideBean guideBean = DbUtils.byGuideId(dbBean, Integer.parseInt(guideId));
        final TextView textView = (TextView) findViewById(R.id.activity_guide_guide_name);
        textView.setText(guideBean.getGuideName());

        populateTours(dbBean, guideId, me);
        final ImageView imageView = (ImageView) findViewById(R.id.activity_guide_image);
        imageView.setImageResource(getResources().getIdentifier(guideBean.getImage(), "drawable", getPackageName()));
    }

    private void populateTours(final DbBean dbBean, final String guideId, final Context me) {
        final List<Map<String, String>> data = getToursData(dbBean, guideId);

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,R.layout.guide_tour_list_item,
                new String[]{"guide_tour_list_item_tour_name", "guide_tour_list_item_tour_id"},
                new int[]{R.id.guide_tour_list_item_tour_name, R.id.guide_tour_list_item_tour_id});

        final ListView listView = (ListView) findViewById(R.id.activity_guide_tour_list);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView textView = (TextView) view.findViewById(R.id.guide_tour_list_item_tour_id);
                final String tourId = textView.getText().toString();
                final Intent intent = new Intent(me, Tour.class);
                intent.putExtra(Tour.TOUR_ID, tourId);
                startActivity(intent);
            }
        });
    }

    private List<Map<String, String>> getToursData(DbBean dbBean, String guideId) {
        final GuideBean guideBean = DbUtils.byGuideId(dbBean, Integer.parseInt(guideId));
        final List<Map<String, String>> data = new LinkedList<>();

        for (final Integer tourId : guideBean.getTours())  {
            final TourBean tourBean = DbUtils.byTourId(dbBean, tourId);
            final Map<String, String> datum = new HashMap<>();

            datum.put("guide_tour_list_item_tour_name", tourBean.getTourName());
            datum.put("guide_tour_list_item_tour_id", String.valueOf(tourBean.getTourId()));
            data.add(datum);
        }

        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guide, menu);
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
