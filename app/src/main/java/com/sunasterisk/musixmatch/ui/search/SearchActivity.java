package com.sunasterisk.musixmatch.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunasterisk.musixmatch.ui.base.BaseActivity;
import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.repository.TrackRepository;
import com.sunasterisk.musixmatch.data.source.remote.TrackRemoteDataSource;

import java.util.List;

public class SearchActivity extends BaseActivity implements SearchContract.View,
        SearchView.OnQueryTextListener {

    private TextView mTextResults;
    private ProgressBar mProgressBar;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private SearchPresenter mSearchPresenter;
    private ResultsSearchAdapter mAdapter;
    private Group mIntroSearchGroup;
    private Group mResultsSearchGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initComponents();
        initData();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search;
    }

    @Override
    protected void initComponents() {
        mTextResults = findViewById(R.id.text_results);
        mProgressBar = findViewById(R.id.search_progress_bar);
        mSearchView = findViewById(R.id.search_view);
        mIntroSearchGroup = findViewById(R.id.intro_search_group);
        mRecyclerView = findViewById(R.id.recycler_results_search);
        mResultsSearchGroup = findViewById(R.id.group_result_search);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        addAction();
        TrackRepository repository = TrackRepository.getInstance(null, TrackRemoteDataSource.getInstance());
        mSearchPresenter = new SearchPresenter(this, repository);
        mSearchPresenter.start();
    }

    @Override
    public void showIntroSearch() {
        mIntroSearchGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideIntroSearch() {
        mIntroSearchGroup.setVisibility(View.GONE);
    }

    @Override
    public void showResultSearchGroup() {
        mResultsSearchGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideResultSearchGroup() {
        mResultsSearchGroup.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSearchResult(List<Track> tracks) {
        showResultSearchGroup();
        hideIntroSearch();
        displayTrack(tracks);
    }

    @Override
    public void showError(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchPresenter.saveRecentSearch(query);
        mSearchView.setQuery(query, false);
        hideResultSearchGroup();
        mSearchPresenter.getTrackFromAPI(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        String recentQuery = intent.getStringExtra(SearchManager.QUERY);
        mSearchPresenter.getTrackFromAPI(recentQuery);
        mSearchView.setQuery(recentQuery, false);
    }

    private void displayTrack(List<Track> tracks) {
        mAdapter = new ResultsSearchAdapter(this, tracks);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addAction() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(this);
    }
}
