package com.sunasterisk.musixmatch.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.source.Callback;
import com.sunasterisk.musixmatch.data.source.TrackDataSource;

import java.util.ArrayList;
import java.util.List;

public class TrackLocalDataSource implements TrackDataSource.Local {
    private Context mContext;
    private static TrackLocalDataSource sInstance;

    public static TrackLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TrackLocalDataSource(context);
        }
        return sInstance;
    }

    private TrackLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void getTracks(Callback<List<Track>> callback) {
        AsyncTask<Void, Void, List<Track>> asyncTask = new AsyncTask<Void, Void, List<Track>>() {
            @Override
            protected List<Track> doInBackground(Void... voids) {
                ContentResolver resolver = mContext.getContentResolver();
                Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, null);
                return getData(cursor);
            }

            @Override
            protected void onPostExecute(List<Track> tracks) {
                super.onPostExecute(tracks);
                if (!tracks.isEmpty()) {
                    callback.getDataSuccess(tracks);
                }
            }
        };
        asyncTask.execute();
    }

    @Override
    public void getTracks(int id, Callback<List<Track>> callback) {
        AsyncTask<Void, Void, List<Track>> asyncTask = new AsyncTask<Void, Void, List<Track>>() {
            @Override
            protected List<Track> doInBackground(Void... voids) {
                String selection = MediaStore.Audio.Media.ALBUM_ID + " = ?";
                ContentResolver resolver = mContext.getContentResolver();
                String[] selectionArgs = new String[]{Integer.toString(id)};
                Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null, selection, selectionArgs, null);
                return getData(cursor);
            }

            @Override
            protected void onPostExecute(List<Track> tracks) {
                super.onPostExecute(tracks);
                if (!tracks.isEmpty()) {
                    callback.getDataSuccess(tracks);
                }
            }
        };
        asyncTask.execute();
    }

    @Override
    public void updateTrack(int trackId, String trackName, String artistName, String albumName) {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, trackId);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Media.TITLE, trackName);
        values.put(MediaStore.Audio.Media.ARTIST, artistName);
        values.put(MediaStore.Audio.Media.ALBUM, albumName);
        resolver.update(uri, values, null, null);
    }

    public List<Track> getData(Cursor cursor) {
        List<Track> tracks = new ArrayList<>();
        int indexTrackId = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int indexTrackName = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexAlbumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        int indexAlbumName = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int indexArtistId = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
        int indexArtistName = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexSize = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int albumId = cursor.getInt(indexAlbumId);
            int trackId = cursor.getInt(indexTrackId);
            String trackName = cursor.getString(indexTrackName);
            int artistId = cursor.getInt(indexArtistId);
            String artistName = cursor.getString(indexArtistName);
            String albumName = cursor.getString(indexAlbumName);
            String data = cursor.getString(indexData);
            long size = cursor.getLong(indexSize);
            long duration = cursor.getLong(indexDuration);
            Track track = new Track.Builder()
                    .setTrackId(trackId)
                    .setTrackName(trackName)
                    .setAlbumId(albumId)
                    .setAlbumName(albumName)
                    .setArtistId(artistId)
                    .setArtistName(artistName)
                    .setData(data)
                    .setSize(size)
                    .setDuration(duration)
                    .build();
            tracks.add(track);
            cursor.moveToNext();
        }
        return tracks;
    }
}
