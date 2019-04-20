package com.sunasterisk.musixmatch.ui.music.playlisttab;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Playlist;
import com.sunasterisk.musixmatch.data.repository.PlaylistRepository;
import com.sunasterisk.musixmatch.data.source.local.PlaylistLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseFragment;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;

import java.util.List;

/**
 * Created by superme198 on 14,April,2019
 * in Music_Match__1.
 */
public class PlaylistTabFragment extends BaseFragment implements PlaylistTabContract.View,
        OnRecyclerItemClickListener<Playlist> {

    private RecyclerView mRecyclerView;
    private PlaylistTabContract.Presenter mPresenter;
    private PlaylistTabAdapter mAdapter;

    public static PlaylistTabFragment newInstance() {
        return new PlaylistTabFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tracks_tab;
    }

    @Override
    protected void initComponents(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_tracks_tab);
    }

    @Override
    protected void initData() {
        PlaylistRepository repository = PlaylistRepository.getsInstance(
                PlaylistLocalDataSource.getsInstance(getContext()));
        mPresenter = new PlaylistTabPresenter(getContext(), this, repository);
        mPresenter.getLocalPlaylist();
    }

    @Override
    public void showPlaylist(List<Playlist> playlists) {
        mAdapter = new PlaylistTabAdapter(getContext());
        mAdapter.setCallBack(this);
        mAdapter.setItems(playlists);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlaylistDoesNotExist() {
        Toast.makeText(getContext(), getString(R.string.text_playlist_does_not_exist), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlaylistRenameSuccessful() {
        Toast.makeText(getContext(), getString(R.string.text_rename_playlist_successful), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlaylistCreateNameSuccessful() {
        Toast.makeText(getContext(), getString(R.string.text_create_playlist_successful), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeletePlaylistSuccessful() {
        Toast.makeText(getContext(), getString(R.string.text_delete_playlist_successful), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeletePlaylistFailed() {
        Toast.makeText(getContext(), getString(R.string.text_error_delete_playlist), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPlaylistNameExist() {
        Toast.makeText(getContext(), getString(R.string.text_error_name_has_exist), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(View view, long pos, Playlist item) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.inflate(R.menu.options_menu_playlist_tab);
        popup.setOnMenuItemClickListener(v -> {
            switch (v.getItemId()) {
                case R.id.play:
                    return true;
                case R.id.add_to_queue:
                    return true;
                case R.id.delete:
                    mPresenter.deletePlaylist(item.getId());
                    return true;
                case R.id.rename_playlist:
                    mPresenter.renamePlaylist(item.getId(), "");
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }

}
