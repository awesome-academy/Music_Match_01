package com.sunasterisk.musixmatch.ui.track_info;

import com.sunasterisk.musixmatch.data.model.Track;

public interface TrackInfoContract {
    interface Presenter {
        void getTrackInfo(Track track);

        void updateTrackInfo(int trackId,
                             String trackName,
                             String artistName,
                             String albumName);
    }

    interface View {
        void showTrackInfo(Track track);
    }
}
