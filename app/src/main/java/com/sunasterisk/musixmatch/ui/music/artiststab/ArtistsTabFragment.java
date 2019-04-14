package com.sunasterisk.musixmatch.ui.music.artiststab;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.data.repository.ArtistRepository;
import com.sunasterisk.musixmatch.data.source.local.ArtistLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseFragment;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by superme198 on 08,April,2019
 * in Music_Match__1.
 */
public class ArtistsTabFragment extends BaseFragment implements ArtistsContract.View, OnRecyclerItemClickListener<Artist> {

    private ArtistsTabAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArtistsContract.Presenter mPresenter;

    public static ArtistsTabFragment newInstance() {
        return new ArtistsTabFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tracks;
    }

    @Override
    protected void initComponents(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_tracks);
    }

    @Override
    protected void initData() {
        mPresenter = new ArtistsTabPresenter(
                ArtistRepository.getInstance(ArtistLocalDataSource.getInstance(getContext())), this);
        mPresenter.getLocalArtists();
    }

    @Override
    public void onItemClicked(Artist item) {

    }

    @Override
    public void onItemClicked(Artist item, List<Artist> items) {

    }

    @Override
    public void showArtists(List<Artist> artists) {
        mAdapter = new ArtistsTabAdapter(getContext(), mFragmentManager);
        mAdapter.setCallBack(this);
        mAdapter.setItems(artists);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showError(Exception e) {

    }
}
