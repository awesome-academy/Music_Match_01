package com.sunasterisk.musixmatch.ui.music.trackstab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.ui.base.BaseAdapter;
import com.sunasterisk.musixmatch.ui.base.BaseTrackViewHolder;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;

import java.util.List;


/**
 * Created by superme198 on 01,April,2019
 * in Music_Match__1.
 */
public class TracksTabAdapter extends BaseAdapter<Track, OnRecyclerItemClickListener<Track>, TracksTabAdapter.MyViewHolder> {

    public TracksTabAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_track, viewGroup, false);
        return new MyViewHolder(mContext, itemView, mCallback);
    }

    public static class MyViewHolder extends BaseTrackViewHolder<Track, OnRecyclerItemClickListener<Track>> {

        private CardView mLogoTrack;
        private TracksTabAdapter mTracksTabAdapter;
        private List<Track> mTracks;
        public MyViewHolder(Context context,
                            @NonNull View itemView, OnRecyclerItemClickListener<Track> callback) {
            super(context, itemView, callback);
            mTracksTabAdapter = new TracksTabAdapter(mContext, mFragmentManager);
            mTracks = mTracksTabAdapter.getItems();
            mLogoTrack = itemView.findViewById(R.id.card_view_logo_track);
            mOptionMore.setOnClickListener(this);
        }

        @Override
        public void bindData(Track track) {
            if (track != null) {
                mItem = track;
                mTextTitle.setText(mItem.getTrackName());
                mTextSubTitle.setText(mItem.getArtistName());
                showLogoTrack();
            }
        }

        @Override
        public void showOptionMenu() {
            PopupMenu popup = new PopupMenu(mContext, mOptionMore);
            popup.inflate(R.menu.options_menu_tracks_tab);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.play:
                        return true;
                    case R.id.add_to_queue:
                        return true;
                    case R.id.add_to_playlist:
                        return true;
                    case R.id.edit_info:
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
                case R.id.button_more:
                    showOptionMenu();
                    break;
                default:
                    System.out.println(mItem.getData());
                    mCallback.onItemClicked(mItem, mTracks);
                    break;
            }
        }

        private void showLogoTrack() {
            mLogoTrack.setVisibility(View.VISIBLE);
        }
    }

}
