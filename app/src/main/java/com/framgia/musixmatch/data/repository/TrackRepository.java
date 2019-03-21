package com.framgia.musixmatch.data.repository;

import com.framgia.musixmatch.data.model.LocalTrack;
import com.framgia.musixmatch.data.source.Callback;
import com.framgia.musixmatch.data.source.TrackDataSource;
import com.framgia.musixmatch.data.source.local.TrackLocalDataSource;

import java.util.List;

public class TrackRepository implements TrackDataSource.LocalDataSource {
    private static TrackRepository sInstance;
    private TrackLocalDataSource mLocalDataSource;

    private TrackRepository(TrackLocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static TrackRepository getInstance(TrackLocalDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new TrackRepository(localDataSource);
        }
        return sInstance;
    }

    @Override
    public void getTracks(Callback<List<LocalTrack>> callback) {
        mLocalDataSource.getTracks(callback);
    }
}
