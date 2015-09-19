package com.cruv.tourguidesupreme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cruv.tourguidesupreme.bean.DbBean;
import com.cruv.tourguidesupreme.bean.LinkBean;
import com.cruv.tourguidesupreme.bean.LocationBean;
import com.cruv.tourguidesupreme.db.DB;
import com.cruv.tourguidesupreme.db.DbUtils;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Location extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    public static final String LOCATION_ID = "com.cruv.tourguidesupreme.Location.LOCATION_ID";
    private static final int RECOVERY_REQUEST = 1;

    private YouTubePlayerView youTubeView;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        final Intent intent = getIntent();
        final Integer locatiodId = Integer.parseInt(intent.getStringExtra(LOCATION_ID));
        final DbBean dbBean = DB.getDB(getResources());
        final LocationBean locationBean = DbUtils.byLocationId(dbBean, locatiodId);
        final TextView textView = (TextView) findViewById(R.id.activity_location_location_name);
        textView.setText(locationBean.getLocationName());

        populateLinks(locationBean);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

        final TextView waze = (TextView) findViewById(R.id.activity_location_geo_url);
        waze.setText(Html.fromHtml("<a href=\"" + locationBean.getGeo() + "\">Google Maps<\\a>"));
        waze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(locationBean.getGeo()));
                startActivity(intent);
            }
        });
        videoId = locationBean.getVideoId();
    }

    private void populateLinks(final LocationBean locationBean) {
        final List<Map<String, String>> data = getLinksData(locationBean);
        final Context me = this;

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.link_list_item,
                new String[]{"link_list_item_link"},
                new int[]{R.id.link_list_item_link}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final LayoutInflater inflater = (LayoutInflater) me.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.link_list_item, parent, false);
                final TextView textView = (TextView) rowView.findViewById(R.id.link_list_item_link);
                final LinkBean linkBean =locationBean.getLinks().get(position);
                textView.setText(Html.fromHtml("<a href=\"" + linkBean.getLink() + "\">" + linkBean.getDescription() + "<\\a>"));

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(linkBean.getLink()));
                        startActivity(intent);
                    }
                });

                return textView;
            }
        };

        final ListView listView = (ListView) findViewById(R.id.activity_location_links);
        listView.setAdapter(simpleAdapter);

    }

    private List<Map<String, String>> getLinksData(LocationBean locationBean) {
        final List<Map<String, String>> data = new LinkedList<>();

        for (final LinkBean linkBean : locationBean.getLinks()) {
            final Map<String, String> datum = new HashMap<>();

            datum.put("link_list_item_link", linkBean.getLink());
            data.add(datum);
        }

        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(videoId);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
