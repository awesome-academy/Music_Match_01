package com.sunasterisk.musixmatch.ui.music.artiststab;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.data.repository.ArtistRepository;
import com.sunasterisk.musixmatch.data.source.local.ArtistLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseActivity;
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
    private BaseActivity.OnFragmentChangeListener mFragmentChangeListener;
    public static ArtistsTabFragment newInstance() {
        return new ArtistsTabFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentChangeListener = (BaseActivity.OnFragmentChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentChangeListener");
        }
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
    public void onItemClicked(View v, long pos, Artist i) {
        switch (v.getId()) {
            case R.id.button_more:
                showOptionMenu(v, i);
                break;
            default:
                break;
        }
    }

    @Override
    public void showArtists(List<Artist> artists) {
        mAdapter = new ArtistsTabAdapter(getContext());
        mAdapter.setCallBack(this);
        mAdapter.setItems(artists);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showError(Exception e) {

    }

    private void showOptionMenu(View v, Artist artist) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        popup.inflate(R.menu.options_menu_artists_tab);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.play:
                    return true;
                case R.id.add_to_queue:
                    return true;
                case R.id.add_to_playlist:
                    return true;
                default:
                    return false;
            }
        });
        popup.show();

    }
}
