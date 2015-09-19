package com.cruv.tourguidesupreme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class SearchGuideResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SearchGuideResults me = this;
        setContentView(R.layout.activity_search_guide_results);

        final String byGuide = getIntent().getStringExtra(MainActivity.SEARCH_A_GUIDE).toLowerCase();
        final DbBean dbBean = DB.getDB(getResources());
        final List<Map<String, String>> data = getGuidesData(byGuide, dbBean);

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,R.layout.guide_list_item,
                new String[]{"guide_name", "guide_id"},
                new int[]{R.id.guide_name, R.id.guide_id});

        final ListView listView = (ListView) findViewById(R.id.guide_list);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView textView = (TextView) view.findViewById(R.id.guide_id);
                final String guideId = textView.getText().toString();
                final Intent intent = new Intent(me, Guide.class);
                intent.putExtra(Guide.GUIDE_ID, guideId);
                startActivity(intent);
            }
        });
    }

    private List<Map<String, String>> getGuidesData(String byGuide, DbBean dbBean) {
        final List<Map<String, String>> data = new LinkedList<>();

        for (final GuideBean guideBean : dbBean.getGuides()) {
            if (guideBean.getGuideName().toLowerCase().contains(byGuide)) {
                final Map<String, String> guideData = new HashMap<>();

                guideData.put("guide_name", guideBean.getGuideName());
                guideData.put("guide_id", String.valueOf(guideBean.getGuideId()));

                data.add(guideData);
            }
        }
        return data;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_guide_results, menu);
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
