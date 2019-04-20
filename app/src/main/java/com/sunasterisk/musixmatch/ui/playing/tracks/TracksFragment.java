package com.sunasterisk.musixmatch.ui.playing.tracks;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.repository.TrackRepository;
import com.sunasterisk.musixmatch.data.source.local.TrackLocalDataSource;
import com.sunasterisk.musixmatch.data.source.remote.TrackRemoteDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseFragment;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;

import java.util.List;

public class TracksFragment extends BaseFragment implements TracksContract.View,
        OnRecyclerItemClickListener<Track> {
    protected RecyclerView mRecyclerView;
    protected TracksContract.Presenter mPresenter;
    protected TracksAdapter mAdapter;
    protected OnGetTracksListener mCallback;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnGetTracksListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTrackClickListener");
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_tracks;
    }

    @Override
    public void initComponents(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_tracks);
    }

    @Override
    protected void initData() {
        mPresenter = new TracksPresenter(TrackRepository.getInstance(
                TrackLocalDataSource.getInstance(getActivity()),
                TrackRemoteDataSource.getInstance()),
                this);
        mPresenter.getLocalTracks();
    }

    @Override
    public void showLocalTracks(List<Track> tracks) {
        mAdapter = new TracksAdapter(getContext());
        mAdapter.setCallBack(this);
        mAdapter.setItems(tracks);
        mRecyclerView.setAdapter(mAdapter);
        mCallback.onGetTracksSuccess(tracks);
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClicked(View view, long pos, Track item) {

        switch (view.getId()) {
            case R.id.item_track_card:
                mCallback.onPlayed(item);
                break;
            case R.id.button_more:
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.inflate(R.menu.options_menu_tracks_tab);
                popup.setOnMenuItemClickListener(it -> {
                    switch (it.getItemId()) {
                        case R.id.play:
                            return true;
                        case R.id.add_to_queue:
                            return true;
                        case R.id.delete:
                            return true;
                        case R.id.add_to_playlist:
                            return true;
                        default:
                            return false;
                    }
                });
                popup.show();
                break;
        }
    }

    public interface OnGetTracksListener {
        void onPlayed(Track track);

        void onGetTracksSuccess(List<Track> tracks);
    }
}
