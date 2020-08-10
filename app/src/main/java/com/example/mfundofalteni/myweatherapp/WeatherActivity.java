package com.example.mfundofalteni.myweatherapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends AppCompatActivity implements RecyclerAdapter.OnDayClickListener {

    public SwipeRefreshLayout swipeRefreshLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    ShimmerFrameLayout shimmerFrameLayout1;
    PrefsManager prefsManager;
    public List<WeatherModel> list;
    public List<WeatherModel> single;
    public RecyclerAdapter recyclerAdaper;
    public ViewDayAdapter viewDayAdapter;
    public RecyclerView recyclerView;
    public RecyclerView recyclerView2;
    FragmentManager fm;
    MyAlertDiagFrag myAlertDiagFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        myAlertDiagFrag = MyAlertDiagFrag.newInstance();
        fm = getSupportFragmentManager();
        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                goCheck();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout1 = findViewById(R.id.shimmer_view_container2);
        prefsManager = new PrefsManager(getApplicationContext());
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView2 = findViewById(R.id.my_recycler_Above);
        goCheck();
    }

    public void goCheck() {
        WeatherConnect weatherConnect = new WeatherConnect();
        if (!weatherConnect.checkConnection(getBaseContext())) {
            getDialog();
        } else {
            goGet();
        }
    }

    private void goGet(){
        String lat =  getIntent().getDoubleExtra("lat", 0.0) + "";
        String lng = getIntent().getDoubleExtra("lng", 0.0) + "";
        startDisplay();
        new GetWeather().execute(lat, lng);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onDayClick(int position) {

    }
    private void startDisplay(){
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout1.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerView2.setVisibility(View.GONE);
        shimmerFrameLayout1.setVisibility(View.VISIBLE);
    }
    public void stopDisplay(){
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout1.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout1.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView2.setVisibility(View.VISIBLE);
    }

    private void getDialog(){
        myAlertDiagFrag.show(fm, "");
    }

    public class GetWeather extends AsyncTask<String, String, String> implements RecyclerAdapter.OnDayClickListener {
        @Override
        protected String doInBackground(String[] objects) {

            try{
               String urlString = buildURL(objects);
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int res = httpURLConnection.getResponseCode();
                if(res != 200)
                    getDialog();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String json;
                if((json = bufferedReader.readLine()) != null){
                    return  json;
                }
            }
            catch (Exception e){
                getDialog();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);

            ParseJSON parseJSON = new ParseJSON();
            list = parseJSON.parseWeather(res);
            WeatherModel weatherModel = list.get(0);
            weatherModel.DT = "Today, " + weatherModel.DT;
            populateToday(weatherModel);
            list.remove(0);
            recyclerAdaper = new RecyclerAdapter(list, this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(recyclerAdaper);
            stopDisplay();
        }

        @Override
        public void onDayClick(int position) {
            WeatherModel weatherModel = list.get(position);
            prefsManager.populateAllPrefs(weatherModel);
            Intent intent = new Intent(getApplicationContext(), ViewDay.class);
            startActivity(intent);
        }

        private void populateToday(WeatherModel weatherModel){
            single = new ArrayList<>();
            single.add(weatherModel);
            viewDayAdapter = new ViewDayAdapter(single);
            recyclerView2.setHasFixedSize(true);
            recyclerView2.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView2.setAdapter(viewDayAdapter);
        }

        private String buildURL(String[] objects){
            String lat = objects[0];
            String lng = objects[1];
            String key = getString(R.string.api_key);
            String units = getString(R.string.units);
            String count = getString(R.string.count);
            return "https://api.openweathermap.org/data/2.5/forecast/daily?"
                    +"lat=" + lat
                    +"&lon=" + lng
                    +"&units="+ units
                    +"&cnt=" + count
                    +"&appid=" + key;
        }
    }
}