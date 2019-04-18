package com.sunasterisk.musixmatch.data.repository;

import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.source.Callback;
import com.sunasterisk.musixmatch.data.source.TrackDataSource;

import java.util.List;

public class TrackRepository implements TrackDataSource.Local, TrackDataSource.RemoteDataSource {
    private static TrackRepository sInstance;
    private TrackDataSource.Local mLocal;
    private TrackDataSource.RemoteDataSource mRemote;

    private TrackRepository(TrackDataSource.Local Local) {
        mLocal = Local;
    }

    public static TrackRepository getInstance(TrackDataSource.Local Local) {
        if (sInstance == null) {
            sInstance = new TrackRepository(Local);
        }
        return sInstance;
    }

    private TrackRepository(TrackDataSource.Local local, TrackDataSource.RemoteDataSource remote) {
        mRemote = remote;
        mLocal = local;
    }

    public static TrackRepository getInstance(TrackDataSource.Local local, TrackDataSource.RemoteDataSource remote) {
        if (sInstance == null) {
            sInstance = new TrackRepository(local, remote);
        }
        return sInstance;
    }

    @Override
    public void getTracks(Callback<List<Track>> callback) {
        mLocal.getTracks(callback);
    }

    @Override
    public void getTracks(int id, Callback<List<Track>> callback) {
        mLocal.getTracks(id, callback);
    }

    @Override
    public void updateTrack(int trackId, String trackName, String artistName, String albumName) {
        mLocal.updateTrack(trackId, trackName, artistName, albumName);
    }

    @Override
    public void getTracksByAlbum(int albumID, int limit, TrackDataSource.LoadTrackCallback callback) {
        mRemote.getTracksByAlbum(albumID, limit, callback);
    }

    @Override
    public void searchTrack(String searchKey, int limit, TrackDataSource.LoadTrackCallback callback) {
        mRemote.searchTrack(searchKey, limit, callback);
    }

    @Override
    public void getLyrics(String trackName, String artistName, Callback<String> callback) {
        mRemote.getLyrics(trackName, artistName, callback);
    }
}
