package com.sunasterisk.musixmatch.data.repository;

import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.data.source.ArtistDataSource;
import com.sunasterisk.musixmatch.data.source.Callback;

import java.util.List;

public class ArtistRepository implements ArtistDataSource.Local {
    private static ArtistRepository sInstance;
    private ArtistDataSource.Local mLocal;

    private ArtistRepository(ArtistDataSource.Local local) {
        mLocal = local;
    }

    public static ArtistRepository getInstance(ArtistDataSource.Local local) {
        if (sInstance == null) {
            sInstance = new ArtistRepository(local);
        }
        return sInstance;
    }

    @Override
    public void getArtists(Callback<List<Artist>> callback) {
        mLocal.getArtists(callback);
    }
}
