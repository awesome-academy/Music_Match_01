package com.sunasterisk.musixmatch.ui.music.artiststab.artistdetails;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.ViewGroup;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.ui.base.BaseAdapter;
import com.sunasterisk.musixmatch.ui.base.BaseTrackViewHolder;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;
import com.sunasterisk.musixmatch.ui.music.albumstab.albumdetails.AlbumDetailsFragment;
import com.sunasterisk.musixmatch.utils.ActivityUtils;

/**
 * Created by superme198 on 13,April,2019
 * in Music_Match__1.
 */
public class ArtistDetailsAdapter extends BaseAdapter<Album, OnRecyclerItemClickListener<Album>, ArtistDetailsAdapter.ArtistDetailsViewHolder> {

    public static final String ARGUMENT_ALBUMS_ITEM = "ARGUMENT_ALBUMS_ITEM";

    public ArtistDetailsAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    public static AlbumDetailsFragment getInstance(Album album) {
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_ALBUMS_ITEM, album);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public ArtistDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = inflate(R.layout.item_track, viewGroup, false);
        return new ArtistDetailsViewHolder(mContext, v, mCallback);
    }

    public static class ArtistDetailsViewHolder extends BaseTrackViewHolder<Album, OnRecyclerItemClickListener<Album>> {
        private CardView mLogoTrack;
        private String mSubTitle;

        public ArtistDetailsViewHolder(Context context, @NonNull View itemView, OnRecyclerItemClickListener<Album> callback) {
            super(context, itemView, callback);
            mLogoTrack = itemView.findViewById(R.id.card_view_logo_track);
            itemView.setOnClickListener(this);
        }

        @Override
        public void showOptionMenu() {
            PopupMenu popup = new PopupMenu(mContext, mOptionMore);
            popup.inflate(R.menu.options_menu_artist_details);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.play_all:
                        return true;
                    case R.id.shuffle_all:
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

        @Override
        public void bindData(Album album) {
            if (album != null) {
                StringBuilder stringBuilder = new StringBuilder();
                mItem = album;
                mTextTitle.setText(album.getAlbumName());
                stringBuilder.append(getNumberAlbumTrackStr(album.getNumberOfSongs()));
                mSubTitle = String.format(stringBuilder.toString(),
                        album.getNumberOfSongs());
                mTextSubTitle.setText(mSubTitle);
                showAlbumArt();
                mLogoTrack.setForeground(Drawable.createFromPath(album.getAlbumArt()));
            }
        }

        private String getNumberAlbumTrackStr(int count) {
            return count > 1 ? mContext.getResources().getString(R.string.text_tracks) :
                    mContext.getResources().getString(R.string.text_track);
        }

        private void showAlbumArt(){
            mLogoTrack.setVisibility(View.VISIBLE);
        }
    }
}
