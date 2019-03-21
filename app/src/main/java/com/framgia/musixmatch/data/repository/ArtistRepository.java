package com.framgia.musixmatch.data.repository;

import com.framgia.musixmatch.data.model.Artist;
import com.framgia.musixmatch.data.source.ArtistDataSource;
import com.framgia.musixmatch.data.source.Callback;
import com.framgia.musixmatch.data.source.local.ArtistLocalDataSource;

import java.util.List;

public class ArtistRepository implements ArtistDataSource.LocalDataSource {
    private static ArtistRepository sInstance;
    private ArtistLocalDataSource mLocalDataSource;

    private ArtistRepository(ArtistLocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static ArtistRepository getInstance(ArtistLocalDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new ArtistRepository(localDataSource);
        }
        return sInstance;
    }

    @Override
    public void getArtists(Callback<List<Artist>> callback) {

    }
}
