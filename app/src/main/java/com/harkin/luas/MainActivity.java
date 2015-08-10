package com.harkin.luas;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.harkin.luas.network.models.Tram;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import io.fabric.sdk.android.Fabric;

/**
 * @author Henry Larkin @harkinabout
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipeLayout) SwipeRefreshLayout swipeLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    private final List<Tram> trams = new ArrayList<>();

    private RecyclerView.Adapter adapter;
    private Presenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setEnabled(true);

        presenter = new Presenter(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(this, trams);
        mRecyclerView.setAdapter(adapter);

        presenter.loadStops();
    }

    @Override protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override public void onRefresh() {
        presenter.refresh();
    }

    @DebugLog public void displayTimes(String currStop, List<Tram> displayTrams) {
        swipeLayout.setRefreshing(false);

        toolbar.setSubtitle( currStop);

        trams.clear();
        trams.addAll(displayTrams);
        adapter.notifyDataSetChanged();
    }
}
