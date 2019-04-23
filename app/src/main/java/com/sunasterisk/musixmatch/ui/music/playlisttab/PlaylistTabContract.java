package com.sunasterisk.musixmatch.ui.music.playlisttab;

import com.sunasterisk.musixmatch.data.model.Playlist;

import java.util.List;

/**
 * Created by superme198 on 14,April,2019
 * in Music_Match__1.
 */
public interface PlaylistTabContract {

    interface View {

        void showPlaylist(List<Playlist> playlists);

        void showError(Exception e);

        void onPlaylistDoesNotExist();

        void onPlaylistRenameSuccessful();

        void onPlaylistCreateNameSuccessful();

        void onDeletePlaylistSuccessful();

        void onDeletePlaylistFailed();

        void onPlaylistNameExist();

        void onDuplicatePrePlaylist();
    }

    interface Presenter {
        void getLocalPlaylist();

        void renamePlaylist(long id, String newName);

        void deletePlaylist(long id);
    }
}
