package com.sunasterisk.musixmatch.data.source;

import com.sunasterisk.musixmatch.data.model.Track;

import java.util.List;

public interface TrackDataSource {
    interface Local {
        void getTracks(Callback<List<Track>> callback);

        void getTracks(int id, Callback<List<Track>> callback);

        void updateTrack(int trackId, String trackName, String artistName, String albumName);
    }

    interface RemoteDataSource {
        void getTracksByAlbum(int albumID, int limit, LoadTrackCallback callback);

        void searchTrack(String searchKey, int limit, LoadTrackCallback callback);

        void getLyrics(String trackName, String artistName, Callback<String> callback);
    }

    interface LoadTrackCallback {
        void onSongsLoaded(List<Track> tracks);

        void onDataNotAvailable(Exception e);
    }
}
