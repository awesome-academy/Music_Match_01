package com.framgia.musixmatch.data.source;

import com.framgia.musixmatch.data.model.LocalTrack;

import java.util.List;

public class TrackDataSource {
    public interface LocalDataSource {
        void getTracks(Callback<List<LocalTrack>> callback);
    }
}
