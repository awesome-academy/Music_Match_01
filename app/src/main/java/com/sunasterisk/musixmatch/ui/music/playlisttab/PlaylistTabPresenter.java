package com.sunasterisk.musixmatch.ui.music.playlisttab;

import android.content.Context;

import com.sunasterisk.musixmatch.data.model.Playlist;
import com.sunasterisk.musixmatch.data.repository.PlaylistRepository;
import com.sunasterisk.musixmatch.data.source.Callback;
import com.sunasterisk.musixmatch.data.source.PlaylistDataSource;

import java.util.List;

/**
 * Created by superme198 on 14,April,2019
 * in Music_Match__1.
 */
public class PlaylistTabPresenter implements PlaylistTabContract.Presenter {

    private PlaylistTabContract.View mView;
    private PlaylistRepository mRepository;
    private Context mContext;

    public PlaylistTabPresenter(Context context, PlaylistTabContract.View view, PlaylistRepository repository) {
        mRepository = repository;
        mView = view;
        mContext = context;
    }

    @Override
    public void getLocalPlaylist() {
        mRepository.getPlaylist(new Callback<List<Playlist>>() {
            @Override
            public void getDataSuccess(List<Playlist> data) {
                mView.showPlaylist(data);
            }

            @Override
            public void getDataFailure(Exception e) {
                mView.showError(e);
            }
        });
    }

    @Override
    public void renamePlaylist(long id, String newName) {

        mRepository.renamePlaylist(id, newName, new PlaylistDataSource.RenamingPlaylistCallback() {
            @Override
            public void onDuplicate() {
                mView.onDuplicatePrePlaylist();
            }

            @Override
            public void onSuccess() {
                mView.onPlaylistRenameSuccessful();
            }

            @Override
            public void onExist() {
                mView.onPlaylistNameExist();
            }

        });
    }

    @Override
    public void deletePlaylist(long id) {
        mRepository.deletePlaylist(id, new PlaylistDataSource.DeletingPlaylistCallback() {
            @Override
            public void onNotExist() {
                mView.onPlaylistDoesNotExist();
            }

            @Override
            public void onDeleteSuccess() {
                mView.onDeletePlaylistSuccessful();
            }
        });

    }
}
