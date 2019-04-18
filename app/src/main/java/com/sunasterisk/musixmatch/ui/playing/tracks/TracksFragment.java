package com.sunasterisk.musixmatch.ui.playing.tracks;

import android.content.Context;
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
    public void onItemClicked(Track item) {
        mCallback.onPlayed(item);
    }

    @Override
    public void onItemClicked(long id) {

    }

    public void onTrackInfoUpdated() {
        mPresenter.getLocalTracks();
    }

    public interface OnGetTracksListener {
        void onPlayed(Track track);

        void onGetTracksSuccess(List<Track> tracks);
    }
}
