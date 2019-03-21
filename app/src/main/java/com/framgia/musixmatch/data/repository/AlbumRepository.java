package com.framgia.musixmatch.data.repository;

import com.framgia.musixmatch.data.model.Album;
import com.framgia.musixmatch.data.source.AlbumDataSource;
import com.framgia.musixmatch.data.source.Callback;
import com.framgia.musixmatch.data.source.local.AlbumLocalDataSource;

import java.util.List;

public class AlbumRepository implements AlbumDataSource.LocalDataSource {
    private static AlbumRepository sInstance;
    private AlbumLocalDataSource mLocalDataSource;

    private AlbumRepository(AlbumLocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public static AlbumRepository getInstance(AlbumLocalDataSource localDataSource) {
        if (sInstance == null) {
            sInstance = new AlbumRepository(localDataSource);
        }
        return sInstance;
    }

    @Override
    public void getAlbums(Callback<List<Album>> callback) {
        mLocalDataSource.getAlbums(callback);
    }
}
