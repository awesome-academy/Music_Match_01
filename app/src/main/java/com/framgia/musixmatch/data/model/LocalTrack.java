package com.framgia.musixmatch.data.model;

public class LocalTrack extends Track {
    private String mData;
    private long mDuration;
    private long mSize;

    public LocalTrack(int trackId, String trackName, int albumId, String albumName, int artistId, String artistName, String data, long size, long duration) {
        super(trackId, trackName, albumId, albumName, artistId, artistName);
        mData = data;
        mSize = size;
        mDuration = duration;
    }

    public String getData() {
        return mData;
    }

    public long getSize() {
        return mSize;
    }

    public long getDuration() {
        return mDuration;
    }
}
