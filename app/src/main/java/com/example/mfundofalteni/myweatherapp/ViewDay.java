package com.example.mfundofalteni.myweatherapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import com.facebook.shimmer.ShimmerFrameLayout;
import java.util.ArrayList;
import java.util.List;

public class ViewDay extends AppCompatActivity {


    ShimmerFrameLayout shimmerFrameLayout;
    public List<WeatherModel> single;
    public ViewDayAdapter viewDayAdapter;
    public RecyclerView recyclerView;
    PrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_day);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        //actionBar.setTitle("");
        actionBar.setElevation(0);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        shimmerFrameLayout = findViewById(R.id.shimmer_container);
        recyclerView = findViewById(R.id.my_recycler_Above);
        prefsManager = new PrefsManager(getApplicationContext());
        shimmerFrameLayout.startShimmer();
        display();
    }


    private void display(){
        single = new ArrayList<>();
        single.add(prefsManager.getAllPrefs());
        viewDayAdapter = new ViewDayAdapter(single);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(viewDayAdapter);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
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
}