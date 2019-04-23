package com.sunasterisk.musixmatch.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.sunasterisk.musixmatch.data.model.Playlist;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.source.Callback;
import com.sunasterisk.musixmatch.data.source.PlaylistDataSource;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by superme198 on 14,April,2019
 * in Music_Match__1.
 */
public class PlaylistLocalDataSource implements PlaylistDataSource {

    private static final String CONTENT_URI_NAME = "external";
    private static final String CONTENT_URI_MEDIA = "content://media";
    private static final int FIRST_ELEMENT_INDEX = 0;
    private static final int ERROR_INDEX = -1;
    private static PlaylistLocalDataSource sInstance;
    private ContentResolver mContentResolver;

    private PlaylistLocalDataSource(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public static PlaylistLocalDataSource getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PlaylistLocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public void getPlaylist(Callback<List<Playlist>> callback) {
        AsyncTask<Void, Void, List<Playlist>> asyncTask = new AsyncTask<Void, Void, List<Playlist>>() {
            private List<Playlist> mPlaylists;
            private Exception mException;

            @Override
            protected List<Playlist> doInBackground(Void... voids) {
                try {
                    mPlaylists = getLocalPlaylist();
                } catch (Exception e) {
                    mException = e;
                }

                return mPlaylists;
            }

            @Override
            protected void onPostExecute(List<Playlist> playlists) {
                super.onPostExecute(playlists);
                if (playlists != null) {
                    callback.getDataSuccess(playlists);
                } else {
                    callback.getDataFailure(mException);
                }

            }
        };
        asyncTask.execute();
    }

    @Override
    public Uri addSongToPlaylist(long id, Track track) {
        if (id < 0) {
            return null;
        }
        // Find the greatest PLAY_ORDER in the playlist
        int lastPlaylistId = getLargestPos(id);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, ++lastPlaylistId);
        contentValues.put(MediaStore.Audio.Playlists.Members.AUDIO_ID, track.getTrackId());
        Uri uri = mContentResolver.insert(MediaStore.Audio.Playlists.Members.getContentUri(CONTENT_URI_NAME, id), contentValues);
        mContentResolver.notifyChange(Uri.parse(CONTENT_URI_MEDIA), null);
        return uri;
    }

    @Override
    public void createPlaylist(String name, CreatingPlaylistCallBack callback) {
        if (name.length() == 0) {
            callback.onInvalid();
            return;
        }
        long id = getPlaylistId(name);

        if (!isExistPlaylist(id)) {
            // We need to create a new playlist.
            ContentValues values = new ContentValues();
            values.put(MediaStore.Audio.Playlists.NAME, name);
            Uri uri = mContentResolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, values);
            id = Long.parseLong(uri.getLastPathSegment());
            callback.onSuccess();
        } else {
            callback.onExist();
        }
    }

    @Override
    public long getPlaylistId(String name) {
        long id = ERROR_INDEX;
        Cursor cursor = mContentResolver.query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Playlists._ID},
                MediaStore.Audio.Playlists.NAME + "=?",
                new String[]{name}, null);

        if (cursor != null && cursor.moveToNext()) {
            id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Playlists._ID));
            cursor.close();
        }

        return id;
    }

    @Override
    public void deletePlaylist(long id, DeletingPlaylistCallback callback) {
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, id);
        if (mContentResolver.delete(uri, null, null) >= 0) {
            callback.onDeleteSuccess();
        } else {
            callback.onNotExist();
        }
    }

    @Override
    public int removeTrackFromPlaylist(long playlistId, Track track) {
        return mContentResolver.delete(
                MediaStore.Audio.Playlists.Members.getContentUri(CONTENT_URI_NAME, playlistId),
                MediaStore.Audio.Playlists.Members._ID + " = ? ", new String[]{String.valueOf(track.getTrackId())});
    }

    @Override
    public void renamePlaylist(long id, String newName, RenamingPlaylistCallback callback) {
        long existingId = getPlaylistId(newName);
        // We are already called the requested name; nothing to do.
        if (existingId == id) {
            callback.onDuplicate();
            return;
        }
        if (isExistPlaylist(existingId)) {
            callback.onExist();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, newName);
        mContentResolver.update(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, values, "_id=" + id, null);
        callback.onSuccess();
    }

    private int countPlaylist(final long id) {
        Cursor cursor = null;
        try {
            cursor = mContentResolver.query(
                    MediaStore.Audio.Playlists.Members.getContentUri(CONTENT_URI_NAME, id),
                    new String[]{
                            MediaStore.Audio.Playlists.Members.AUDIO_ID,
                    }, null, null,
                    MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER);

            if (cursor != null) {
                return cursor.getCount();
            }
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return 0;
    }

    private List<Playlist> getLocalPlaylist() {
        List<Playlist> playlists = new ArrayList<>();
        Uri tempPlaylistURI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContentResolver.query(tempPlaylistURI,
                null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;//don't have list on phone
        }
        int indexId = cursor.getColumnIndex(MediaStore.Audio.Playlists._ID);
        int indexName = cursor.getColumnIndex(MediaStore.Audio.Playlists.NAME);
        while (cursor.moveToNext()) {
            int playlistId = cursor.getInt(indexId);
            String playlistName = cursor.getString(indexName);
            int numSongs = countPlaylist(playlistId);
            Playlist playList = new Playlist(playlistId, playlistName, numSongs);
            playlists.add(playList);
        }
        return playlists;
    }

    private int getLargestPos(long id) {
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri(CONTENT_URI_NAME, id);
        String[] projection = new String[]{MediaStore.Audio.Playlists.Members.PLAY_ORDER};
        Cursor cursor = mContentResolver.query(uri, projection, null, null, null);
        int base = FIRST_ELEMENT_INDEX;
        if (cursor.moveToLast()) {
            base = cursor.getInt(FIRST_ELEMENT_INDEX);
        }
        cursor.close();
        return base;
    }

    private boolean isExistPlaylist(long playlistId) {
        return playlistId >= 0;
    }

}
