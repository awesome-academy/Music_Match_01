package com.sunasterisk.musixmatch.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.ui.base.BaseActivity;
import com.sunasterisk.musixmatch.ui.main.home.HomeFragment;
import com.sunasterisk.musixmatch.ui.music.MusicFragment;
import com.sunasterisk.musixmatch.ui.playing.tracks.TracksFragment;
import com.sunasterisk.musixmatch.ui.search.SearchActivity;

import java.util.List;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        TracksFragment.OnGetTracksListener, BaseActivity.OnFragmentChangeListener {
    private BottomNavigationView mBottomNavigationView;

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
                replaceFragment(getSupportFragmentManager(), HomeFragment.newInstance());
                break;
            case R.id.action_music:
                replaceFragment(getSupportFragmentManager(), MusicFragment.newInstance());
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

    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {

    }

    @Override
    public void onAddFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        addFragmentToActivity(fragmentManager, fragment, frameId);
    }

    @Override
    public void onReplaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        replaceFragment(fragmentManager, fragment);
    }
}
