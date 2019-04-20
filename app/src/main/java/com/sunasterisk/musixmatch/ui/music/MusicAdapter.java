package com.sunasterisk.musixmatch.ui.music;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.ui.music.albumstab.AlbumsTabFragment;
import com.sunasterisk.musixmatch.ui.music.artiststab.ArtistsTabFragment;
import com.sunasterisk.musixmatch.ui.music.playlisttab.PlaylistTabFragment;
import com.sunasterisk.musixmatch.ui.music.trackstab.TracksTabFragment;

/**
 * Created by superme198 on 10,April,2019
 * in Music_Match__1.
 */
public class MusicAdapter extends FragmentPagerAdapter {

    private static final int ALBUM =0;
    private static final int ARTIST =1;
    private static final int TRACK = 2;
    private static final int PLAYLIST = 3;
    private static final int[] TABS = {ALBUM, ARTIST, TRACK, PLAYLIST};
    private Context mContext;

    public MusicAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case ALBUM:
                return AlbumsTabFragment.newInstance();
            case ARTIST:
                return ArtistsTabFragment.newInstance();
            case TRACK:
                return TracksTabFragment.newInstance();
            case PLAYLIST:
                return PlaylistTabFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return TABS.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case ALBUM:
                return mContext.getString(R.string.text_album_tab);
            case ARTIST:
                return mContext.getString(R.string.text_artist_tab);
            case TRACK:
                return mContext.getString(R.string.text_track_tab);
            case PLAYLIST:
                return mContext.getString(R.string.text_playlist_tab);
        }
        return "";
    }
}
