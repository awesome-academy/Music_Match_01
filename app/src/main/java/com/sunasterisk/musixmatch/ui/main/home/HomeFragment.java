package com.sunasterisk.musixmatch.ui.main.home;

import android.view.View;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.ui.base.BaseFragment;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;
import com.sunasterisk.musixmatch.ui.playing.tracks.TracksFragment;

import java.util.List;

public class HomeFragment extends BaseFragment implements TracksFragment.OnGetTracksListener, OnRecyclerItemClickListener<Track> {
    protected TracksFragment.OnGetTracksListener mCallback;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initComponents(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onPlayed(Track track) {

    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {

    }

    @Override
    public void onItemClicked(Track item) {
        mCallback.onPlayed(item);
    }

    @Override
    public void onItemClicked(Track item, List<Track> items) {

    }
}
