package com.ms.RecyclerViewLoader;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.recyclerview) RecyclerView mRecyclerView;

    private DemoAdapter mAdapter;
    private List<User> mItems;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }


    private void setupAdapter() {
        mAdapter = new DemoAdapter(this);
        mAdapter.setHasStableIds(true);
        mItems = getData(0);
        mAdapter.setItems(mItems);
    }

    private void loadMoreData() {

        mAdapter.showLoading(true);
        mAdapter.notifyDataSetChanged();

        // Load data after delay
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<User> newItems = getData(mItems.size());
                mItems.addAll(newItems);
                mAdapter.setItems(mItems); // No need of this
                mAdapter.showLoading(false);
                mAdapter.notifyDataSetChanged();
            }
        }, 1500);

    }

    private List<User> getData(int start) {
        List<User> items = new ArrayList<>();
        for (int i=start; i<start+12; i++) {
            items.add(new User(i, "Shofikul Islma " + i));
        }

        return items;
    }
}
