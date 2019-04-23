package com.sunasterisk.musixmatch.data.source;

import android.net.Uri;

import com.sunasterisk.musixmatch.data.model.Playlist;
import com.sunasterisk.musixmatch.data.model.Track;

import java.util.List;

/**
 * Created by superme198 on 14,April,2019
 * in Music_Match__1.
 */
public interface PlaylistDataSource {

    void getPlaylist(Callback<List<Playlist>> callback);

    Uri addSongToPlaylist(long Id, Track track);

    void createPlaylist(String name, CreatingPlaylistCallBack callback);

    long getPlaylistId(String name);

    void deletePlaylist(long id, DeletingPlaylistCallback callback);

    int removeTrackFromPlaylist(long id, Track track);

    void renamePlaylist(long id, String newName, RenamingPlaylistCallback callback);

    interface CreatingPlaylistCallBack {
        void onSuccess();

        void onExist();

        void onInvalid();
    }

    interface RenamingPlaylistCallback {
        void onDuplicate();

        void onSuccess();

        void onExist();
    }

    interface DeletingPlaylistCallback {
        void onNotExist();

        void onDeleteSuccess();
    }
}
