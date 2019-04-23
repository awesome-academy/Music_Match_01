package com.sunasterisk.musixmatch.ui.music.albumstab;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.repository.AlbumRepository;
import com.sunasterisk.musixmatch.data.source.local.AlbumLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseActivity;
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
    private BaseActivity.OnFragmentChangeListener mFragmentChangeListener;

    public static AlbumsTabFragment newInstance() {
        return new AlbumsTabFragment();
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
    public void onItemClicked(View v, long pos, Album item) {
        switch (v.getId()) {
            case R.id.button_more_album:
                showOptionMenu(v, item);
                break;
            default:
                mFragmentChangeListener.onReplaceFragment(getFragmentManager(),
                        AlbumsTabAdapter.getAlbumDetailsFragment(item));
                break;
        }
    }

    @Override
    public void showLocalAlbums(List<Album> albums) {
        mAdapter = new AlbumsTabAdapter(getContext());
        mAdapter.setItems(albums);
        mAdapter.setCallBack(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_COLUMNS));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showError(Exception e) {

    }

    private void showOptionMenu(View view, Album album) {
        PopupMenu popup = new PopupMenu(getContext(), view);
        popup.inflate(R.menu.options_menu_albums_tab);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.play:
                    return true;
                case R.id.add_to_queue:
                    return true;
                case R.id.add_to_playlist:
                    return true;
                case R.id.edit_info:
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

}
