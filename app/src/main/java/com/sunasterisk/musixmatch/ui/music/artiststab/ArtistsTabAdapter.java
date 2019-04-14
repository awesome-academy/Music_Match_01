package com.sunasterisk.musixmatch.ui.music.artiststab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.ViewGroup;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.ui.base.BaseAdapter;
import com.sunasterisk.musixmatch.ui.base.BaseTrackViewHolder;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;
import com.sunasterisk.musixmatch.ui.music.artiststab.artistdetails.ArtistDetailsFragment;
import com.sunasterisk.musixmatch.utils.ActivityUtils;

/**
 * Created by superme198 on 08,April,2019
 * in Music_Match__1.
 */
public class ArtistsTabAdapter extends BaseAdapter<Artist, OnRecyclerItemClickListener<Artist>, ArtistsTabAdapter.ArtistTabViewHolder> {

    public static final String ARGUMENT_ARTIST_ITEM = "ARGUMENT_ARTIST_ITEM";

    public ArtistsTabAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    public static ArtistDetailsFragment getInstance(Artist artist) {
        ArtistDetailsFragment fragment = new ArtistDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_ARTIST_ITEM, artist);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public ArtistTabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = inflate(R.layout.item_track, viewGroup, false);
        return new ArtistTabViewHolder(mContext, v, mCallback);
    }

    public static class ArtistTabViewHolder extends BaseTrackViewHolder<Artist, OnRecyclerItemClickListener<Artist>> {

        private String mSubTitle;

        public ArtistTabViewHolder(Context context, View itemView, OnRecyclerItemClickListener<Artist> mCallback) {
            super(context, itemView, mCallback);
            mTextTitle.setOnClickListener(this);
            mTextSubTitle.setOnClickListener(this);
            mOptionMore.setOnClickListener(this);
        }

        @Override
        public void bindData(Artist artist) {
            if (artist != null) {
                StringBuilder stringBuilder = new StringBuilder();
                mItem = artist;
                mTextTitle.setText(artist.getArtistName());
                stringBuilder.append(getNumberAlbumStr(artist.getNumberOfAlbums()))
                        .append(getNumberAlbumTrackStr(artist.getNumberOfTracks()));
                mSubTitle = String.format(stringBuilder.toString(),
                        artist.getNumberOfAlbums(), artist.getNumberOfTracks());
                mTextSubTitle.setText(mSubTitle);
            }
        }

        @Override
        public void showOptionMenu() {
            PopupMenu popup = new PopupMenu(mContext, mOptionMore);
            popup.inflate(R.menu.options_menu_artists_tab);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.play:
                        return true;
                    case R.id.add_to_queue:
                        return true;
                    case R.id.add_to_playlist:
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.item_track_card:
                case R.id.text_title:
                case R.id.text_subtitle:
                    if (mCallback != null) {
                        mCallback.onItemClicked(mItem);
                        ActivityUtils.replaceFragment(mFragmentManager, getInstance(mItem));
                    }
                    break;
                case R.id.button_more:
                    showOptionMenu();
                    break;
            }
        }

        private String getNumberAlbumStr(int count) {
            return count > 1 ? mContext.getResources().getString(R.string.text_albums) :
                    mContext.getResources().getString(R.string.text_album);
        }

        private String getNumberAlbumTrackStr(int count) {
            return count > 1 ? mContext.getResources().getString(R.string.text_tracks) :
                    mContext.getResources().getString(R.string.text_track);
        }
    }

}
