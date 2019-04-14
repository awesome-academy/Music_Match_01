package com.sunasterisk.musixmatch.ui.music.albumstab;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.repository.AlbumRepository;
import com.sunasterisk.musixmatch.data.source.local.AlbumLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseFragment;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by superme198 on 05,April,2019
 * in Music_Match__1.
 */
public class AlbumsTabFragment extends BaseFragment implements AlbumsContract.View,
        OnRecyclerItemClickListener<Album> {

    protected AlbumsTabAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected AlbumsTabPresenter mPresenter;
    private static final int NUMBER_COLUMNS = 2;

    public static AlbumsTabFragment newInstance() {
        return new AlbumsTabFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_albums_tab;
    }

    @Override
    protected void initComponents(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_albums_tab);
    }

    @Override
    protected void initData() {
        mPresenter = new AlbumsTabPresenter(
                AlbumRepository.getInstance(AlbumLocalDataSource.getInstance(getActivity())),
                this);
        mPresenter.getLocalAlbums();
    }

    @Override
    public void onItemClicked(Album item) {

    }

    @Override
    public void onItemClicked(Album item, List<Album> items) {

    }

    @Override
    public void showLocalAlbums(List<Album> albums) {
        mAdapter = new AlbumsTabAdapter(getContext(), getFragmentManager());
        mAdapter.setItems(albums);
        mAdapter.setCallBack(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_COLUMNS));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showError(Exception e) {

    }
}
