package com.sunasterisk.musixmatch.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.source.AlbumDataSource;
import com.sunasterisk.musixmatch.data.source.Callback;

import java.util.ArrayList;
import java.util.List;

public class AlbumLocalDataSource implements AlbumDataSource.Local {
    private static AlbumLocalDataSource sInstance;
    private Context mContext;

    public static AlbumLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AlbumLocalDataSource(context);
        }
        return sInstance;
    }

    private AlbumLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void getAlbums(Callback<List<Album>> callback) {
        List<Album> albums = new ArrayList<>();
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        cursor.moveToFirst();
        MediaStore.Audio.Albums audioAlbums = new MediaStore.Audio.Albums();
        int indexAlbumId = cursor.getColumnIndex(audioAlbums._ID);
        int indexAlbumName = cursor.getColumnIndex(audioAlbums.ALBUM);
        int indexArtistId = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
        int indexArtistName = cursor.getColumnIndex(audioAlbums.ALBUM);
        int indexNumberOfSongs = cursor.getColumnIndex(audioAlbums.NUMBER_OF_SONGS);
        int indexAlbumArt = cursor.getColumnIndex(audioAlbums.ALBUM_ART);
        while (cursor.isAfterLast() == false) {
            int albumId = cursor.getInt(indexAlbumId);
            String albumName = cursor.getString(indexAlbumName);
            int artistId = cursor.getInt(indexArtistId);
            String artistName = cursor.getString(indexArtistName);
            int numberOfSongs = cursor.getInt(indexNumberOfSongs);
            String albumArt = cursor.getString(indexAlbumArt);
            Album album = new Album.Builder()
                    .setAlbumId(albumId)
                    .setAlbumName(albumName)
                    .setArtistId(artistId)
                    .setArtistName(artistName)
                    .setNumberOfSongs(numberOfSongs)
                    .setAlbumArt(albumArt)
                    .build();
            albums.add(album);
            cursor.moveToNext();
        }
        callback.getDataSuccess(albums);
    }
}
