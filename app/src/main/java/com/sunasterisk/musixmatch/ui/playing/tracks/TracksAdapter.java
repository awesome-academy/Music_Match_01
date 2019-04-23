package com.sunasterisk.musixmatch.ui.playing.tracks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.ui.base.BaseAdapter;
import com.sunasterisk.musixmatch.ui.base.BaseTrackViewHolder;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;

public class TracksAdapter extends BaseAdapter<Track, OnRecyclerItemClickListener<Track>, TracksAdapter.TrackViewHolder> {

    public TracksAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_track, viewGroup, false);
        return new TrackViewHolder(mContext, itemView, mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder trackViewHolder, int i) {
        trackViewHolder.bindData(mItems.get(i));
    }

    public static class TrackViewHolder extends BaseTrackViewHolder<Track, OnRecyclerItemClickListener<Track>> {
        protected ImageView mOptionMore;

        public TrackViewHolder(Context context, View itemView, OnRecyclerItemClickListener<Track> mCallback) {
            super(context, itemView, mCallback);
            mOptionMore = itemView.findViewById(R.id.button_more);
        }

        @Override
        public void bindData(Track track) {
            if (track != null) {
                mItem = track;
                mTextTitle.setText(track.getTrackName());
                mTextSubTitle.setText(track.getArtistName());
            }
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                mCallback.onItemClicked(v, getAdapterPosition(), mItem);
            }

        }
    }
}
