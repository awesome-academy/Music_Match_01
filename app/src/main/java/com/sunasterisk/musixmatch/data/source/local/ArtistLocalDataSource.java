package com.sunasterisk.musixmatch.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.data.source.ArtistDataSource;
import com.sunasterisk.musixmatch.data.source.Callback;

import java.util.ArrayList;
import java.util.List;

public class ArtistLocalDataSource implements ArtistDataSource.Local {
    private Context mContext;
    private static ArtistLocalDataSource sInstance;

    public ArtistLocalDataSource(Context context) {
        mContext = context;
    }

    public static ArtistLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ArtistLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public void getArtists(Callback<List<Artist>> callback) {
        List<Artist> artists = new ArrayList<>();
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        cursor.moveToFirst();
        MediaStore.Audio.Artists audioArtist = new MediaStore.Audio.Artists();
        int indexArtistId = cursor.getColumnIndex(audioArtist._ID);
        int indexArtistName = cursor.getColumnIndex(audioArtist.ARTIST);
        int indexNumberOfAlbums = cursor.getColumnIndex(audioArtist.NUMBER_OF_ALBUMS);
        int indexNumberOfTracks = cursor.getColumnIndex(audioArtist.NUMBER_OF_TRACKS);
        while (cursor.isAfterLast() == false) {
            int artistId = cursor.getInt(indexArtistId);
            String artistName = cursor.getString(indexArtistName);
            int numberOfAlbums = cursor.getInt(indexNumberOfAlbums);
            int numberOfTracks = cursor.getInt(indexNumberOfTracks);
            Artist artist = new Artist.Builder()
                    .setArtistId(artistId)
                    .setArtistName(artistName)
                    .setNumberOfAlbums(numberOfAlbums)
                    .setNumberOfTracks(numberOfTracks)
                    .build();
            artists.add(artist);
            cursor.moveToNext();
        }
        callback.getDataSuccess(artists);
    }
}
