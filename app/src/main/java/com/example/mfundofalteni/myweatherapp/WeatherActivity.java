package com.example.mfundofalteni.myweatherapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
    public RecyclerAdapter recyclerAdapter;
    public ViewDayAdapter viewDayAdapter;
    public RecyclerView recyclerDaily;
    public RecyclerView recyclerToday;
    MyAlertDiagFrag myAlertDiagFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        actionBarInit();
        initObjects();
        goCheckIfReady();
    }

    private void actionBarInit(){
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void initObjects(){

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout1 = findViewById(R.id.shimmer_view_container2);
        recyclerDaily = findViewById(R.id.my_recycler_view);
        recyclerToday = findViewById(R.id.my_recycler_Above);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goCheckIfReady();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void goCheckIfReady() {
        WeatherConnect weatherConnect = new WeatherConnect();
        if (!weatherConnect.checkConnection(getBaseContext())){
            getDialog();
        }else {
            goGetWeather();
        }
    }

    private void goGetWeather(){
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
        recyclerDaily.setVisibility(View.GONE);
        recyclerToday.setVisibility(View.GONE);
        shimmerFrameLayout1.setVisibility(View.VISIBLE);
    }
    public void stopDisplay(){
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout1.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout1.setVisibility(View.GONE);
        recyclerToday.setVisibility(View.VISIBLE);
        recyclerDaily.setVisibility(View.VISIBLE);
    }

    private void getDialog(){
        myAlertDiagFrag = MyAlertDiagFrag.newInstance();
        myAlertDiagFrag.show(getSupportFragmentManager(), "");
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

            try {
                list = parseJSON.parseWeather(res);
                WeatherModel weatherModel = list.get(0);
                weatherModel.Date = "Today, " + weatherModel.Date;
                populateToday(weatherModel);
                list.remove(0);
                recyclerAdapter = new RecyclerAdapter(list, this);
                recyclerDaily.setHasFixedSize(true);
                recyclerDaily.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerDaily.setItemAnimator(new DefaultItemAnimator());
                recyclerDaily.setAdapter(recyclerAdapter);
                stopDisplay();
            } catch (Exception e) {
                getDialog();
            }
        }

        @Override
        public void onDayClick(int position) {
            WeatherModel weatherModel = list.get(position);
            prefsManager = new PrefsManager(getApplicationContext());
            prefsManager.populateAllPrefs(weatherModel);
            Intent intent = new Intent(getApplicationContext(), ViewDay.class);
            startActivity(intent);
        }

        private void populateToday(WeatherModel weatherModel){
            single = new ArrayList<>();
            single.add(weatherModel);
            viewDayAdapter = new ViewDayAdapter(single);
            recyclerToday.setHasFixedSize(true);
            recyclerToday.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
            recyclerToday.setAdapter(viewDayAdapter);
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