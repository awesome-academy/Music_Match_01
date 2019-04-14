package com.sunasterisk.musixmatch.data.repository;

import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.source.AlbumDataSource;
import com.sunasterisk.musixmatch.data.source.Callback;

import java.util.List;

public class AlbumRepository implements AlbumDataSource.Local {
    private static AlbumRepository sInstance;
    private AlbumDataSource.Local mLocal;

    private AlbumRepository(AlbumDataSource.Local local) {
        mLocal = local;
    }

    public static AlbumRepository getInstance(AlbumDataSource.Local local) {
        if (sInstance == null) {
            sInstance = new AlbumRepository(local);
        }
        return sInstance;
    }

    @Override
    public void getAlbums(Callback<List<Album>> callback) {
        mLocal.getAlbums(callback);
    }

    @Override
    public void getAlbums(int artistId, Callback<List<Album>> callback) {
        mLocal.getAlbums(artistId, callback);
    }
}
