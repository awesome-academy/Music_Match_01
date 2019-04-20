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

    long createPlaylist(String name);

    long getPlaylistId(String name);

    int deletePlaylist(long id);

    int removeTrackFromPlaylist(long id, Track track);

    int renamePlaylist(long id, String newName);
}
