package com.framgia.musixmatch.data.source;

import com.framgia.musixmatch.data.model.Album;

import java.util.List;

public class AlbumDataSource {
    public interface LocalDataSource {
        void getAlbums(Callback<List<Album>> callback);
    }
}
