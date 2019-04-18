package com.sunasterisk.musixmatch.ui.track_info;

import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.repository.TrackRepository;

public class TrackInfoPresenter implements TrackInfoContract.Presenter {
    private TrackRepository mRepository;
    private TrackInfoContract.View mView;

    public TrackInfoPresenter(TrackRepository mRepository, TrackInfoContract.View mView) {
        this.mRepository = mRepository;
        this.mView = mView;
    }

    @Override
    public void getTrackInfo(Track track) {
        mView.showTrackInfo(track);
    }

    @Override
    public void updateTrackInfo(int trackId,
                                String trackName,
                                String artistName,
                                String albumName) {
        mRepository.updateTrack(trackId, trackName, artistName, albumName);
    }
}
