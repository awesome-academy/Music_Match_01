package com.sunasterisk.musixmatch.ui.music.artiststab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.ViewGroup;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.ui.base.BaseAdapter;
import com.sunasterisk.musixmatch.ui.base.BaseTrackViewHolder;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;

/**
 * Created by superme198 on 08,April,2019
 * in Music_Match__1.
 */
public class ArtistsTabAdapter extends BaseAdapter<Artist, OnRecyclerItemClickListener<Artist>, ArtistsTabAdapter.ArtistTabViewHolder> {

    public ArtistsTabAdapter(Context context) {
        super(context);
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
        public void onClick(View v) {
            if (mCallback != null) {
                mCallback.onItemClicked(v, getAdapterPosition(), mItem);
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
