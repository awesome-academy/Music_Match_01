package com.sunasterisk.musixmatch.data.repository;

import android.net.Uri;

import com.sunasterisk.musixmatch.data.model.Playlist;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.source.Callback;
import com.sunasterisk.musixmatch.data.source.PlaylistDataSource;

import java.util.List;

/**
 * Created by superme198 on 14,April,2019
 * in Music_Match__1.
 */
public class PlaylistRepository implements PlaylistDataSource {

    private static PlaylistRepository sInstance;
    private PlaylistDataSource mDataSource;

    private PlaylistRepository(PlaylistDataSource dataSource) {
        mDataSource = dataSource;
    }

    public static PlaylistRepository getsInstance(PlaylistDataSource dataSource) {
        if (sInstance == null) {
            sInstance = new PlaylistRepository(dataSource);
        }
        return sInstance;
    }

    @Override
    public void getPlaylist(Callback<List<Playlist>> callback) {
        mDataSource.getPlaylist(callback);
    }

    @Override
    public Uri addSongToPlaylist(long id, Track track) {
        return mDataSource.addSongToPlaylist(id, track);
    }

    @Override
    public long createPlaylist(String name) {
        return mDataSource.createPlaylist(name);
    }

    @Override
    public long getPlaylistId(String name) {
        return mDataSource.getPlaylistId(name);
    }

    @Override
    public int deletePlaylist(long id) {
        return mDataSource.deletePlaylist(id);
    }

    @Override
    public int removeTrackFromPlaylist(long id, Track track) {
        return mDataSource.removeTrackFromPlaylist(id, track);
    }

    @Override
    public int renamePlaylist(long id, String newName) {
        return mDataSource.renamePlaylist(id, newName);
    }


}
