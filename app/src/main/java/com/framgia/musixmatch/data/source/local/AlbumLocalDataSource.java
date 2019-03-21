package com.framgia.musixmatch.data.source.local;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.framgia.musixmatch.application.MusixMatchApplication;
import com.framgia.musixmatch.data.model.Album;
import com.framgia.musixmatch.data.source.AlbumDataSource;
import com.framgia.musixmatch.data.source.Callback;

import java.util.ArrayList;
import java.util.List;

public class AlbumLocalDataSource implements AlbumDataSource.LocalDataSource {
    @Override
    public void getAlbums(Callback<List<Album>> callback) {
        List<Album> albums = new ArrayList<>();
        ContentResolver resolver = MusixMatchApplication.getInstance().getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        cursor.moveToFirst();
        MediaStore.Audio.Albums audioAlbums = new MediaStore.Audio.Albums();
        int indexAlbumId = cursor.getColumnIndex(audioAlbums._ID);
        int indexAlbumName = cursor.getColumnIndex(audioAlbums.ALBUM);
        int indexArtistId = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
        int indexArtistName = cursor.getColumnIndex(audioAlbums.ALBUM);
        int indexNumSongs = cursor.getColumnIndex(audioAlbums.NUMBER_OF_SONGS);
        int indexAlbumArt = cursor.getColumnIndex(audioAlbums.ALBUM_ART);
        while (cursor.isAfterLast() == false) {
            int albumId = cursor.getInt(indexAlbumId);
            String albumName = cursor.getString(indexAlbumName);
            int artistId = cursor.getInt(indexArtistId);
            String artistName = cursor.getString(indexArtistName);
            int numSongs = cursor.getInt(indexNumSongs);
            String albumArt = cursor.getString(indexAlbumArt);
            Album album = new Album(albumId, albumName, artistId, artistName, numSongs, albumArt);
            albums.add(album);
            cursor.moveToNext();
        }
        callback.getDataSuccess(albums);
    }
}
