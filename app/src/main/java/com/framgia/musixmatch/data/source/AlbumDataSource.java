package com.framgia.musixmatch.data.source;

import com.framgia.musixmatch.data.model.Album;

import java.util.List;

public interface AlbumDataSource {
    interface Local {
        void getAlbums(Callback<List<Album>> callback);
    }
}
