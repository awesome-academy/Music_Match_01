package com.framgia.musixmatch.data.source.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.framgia.musixmatch.application.MusixMatchApplication;
import com.framgia.musixmatch.data.model.LocalTrack;
import com.framgia.musixmatch.data.source.Callback;
import com.framgia.musixmatch.data.source.TrackDataSource;

import java.util.ArrayList;
import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.LocalDataSource {
    @Override
    public void getTracks(Callback<List<LocalTrack>> callback) {
        List<LocalTrack> tracks = new ArrayList<>();
        ContentResolver resolver = MusixMatchApplication.getInstance().getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        cursor.moveToFirst();
        MediaStore.Audio.Media audioMedia = new MediaStore.Audio.Media();
        int indexTrackId = cursor.getColumnIndex(audioMedia._ID);
        int indexTrackName = cursor.getColumnIndex(audioMedia.TITLE);
        int indexAlbumId = cursor.getColumnIndex(audioMedia.ALBUM_ID);
        int indexAlbumName = cursor.getColumnIndex(audioMedia.ALBUM);
        int indexArtistId = cursor.getColumnIndex(audioMedia.ARTIST_ID);
        int indexArtistName = cursor.getColumnIndex(audioMedia.ARTIST);
        int indexData = cursor.getColumnIndex(audioMedia.DATA);
        int indexDuration = cursor.getColumnIndex(audioMedia.DURATION);
        int indexSize = cursor.getColumnIndex(audioMedia.SIZE);
        while (cursor.isAfterLast() == false) {
            int trackId = cursor.getInt(indexTrackId);
            String trackName = cursor.getString(indexTrackName);
            int artistId = cursor.getInt(indexArtistId);
            String artistName = cursor.getString(indexArtistName);
            int albumId = cursor.getInt(indexAlbumId);
            String albumName = cursor.getString(indexAlbumName);
            String data = cursor.getString(indexData);
            long size = cursor.getLong(indexSize);
            long duration = cursor.getLong(indexDuration);
            LocalTrack localTrack = new LocalTrack(trackId, trackName, artistId, artistName, albumId, albumName, data, duration, size);
            tracks.add(localTrack);
            cursor.moveToNext();
        }
        callback.getDataSuccess(tracks);
    }
}
