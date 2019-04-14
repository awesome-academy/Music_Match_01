package com.sunasterisk.musixmatch.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.ui.base.BaseActivity;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;
import com.sunasterisk.musixmatch.ui.main.home.HomeFragment;
import com.sunasterisk.musixmatch.ui.music.MusicFragment;
import com.sunasterisk.musixmatch.ui.playing.PlayingActivity;
import com.sunasterisk.musixmatch.ui.playing.tracks.TracksFragment;
import com.sunasterisk.musixmatch.ui.search.SearchActivity;
import com.sunasterisk.musixmatch.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, TracksFragment.OnGetTracksListener, OnRecyclerItemClickListener<Track> {

    public static final String EXTRA_TRACK = "EXTRA_TRACK";
    public static final String EXTRA_TRACKS = "EXTRA_TRACKS";
    protected TracksFragment.OnGetTracksListener mCallback;
    private BottomNavigationView mBottomNavigationView;
    private Track mCurrentTrack;
    private List<Track> mTracks;




    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponents() {
        mBottomNavigationView = findViewById(R.id.navigation_main);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mBottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                ActivityUtils.replaceFragment(getSupportFragmentManager(), HomeFragment.newInstance());
                break;
            case R.id.action_music:
                ActivityUtils.replaceFragment(getSupportFragmentManager(), MusicFragment.newInstance());
                break;
            case R.id.action_search:
                jumpToSearchScreen();
                break;
        }
        return true;
    }

    private void jumpToSearchScreen() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPlayed(Track track) {
        mCurrentTrack = track;
        onItemClicked(track);
    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {
        mTracks = tracks;
    }

    @Override
    public void onItemClicked(Track item) {
        startActivity(PlayingActivity.getPlayingActivity(this, mCurrentTrack, mTracks));
    }

    @Override
    public void onItemClicked(Track item, List<Track> items) {

    }
}
