package com.framgia.musixmatch.data.source;

import com.framgia.musixmatch.data.model.Artist;

import java.util.List;

public class ArtistDataSource {
    public interface LocalDataSource {
        void getArtists(Callback<List<Artist>> callback);
    }
}
