package com.framgia.musixmatch.data.model;

public class Artist {
    private int mArtistId;
    private String mArtistName;
    private int mNumberOfAlbums;
    private int mNumberOfTracks;

    public Artist(int artistId, String artistName, int numberOfAlbums, int numberOfTracks) {
        mArtistId = artistId;
        mArtistName = artistName;
        mNumberOfAlbums = numberOfAlbums;
        mNumberOfTracks = numberOfTracks;
    }

    public int getArtistId() {
        return mArtistId;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public int getNumberOfAlbums() {
        return mNumberOfAlbums;
    }

    public int getNumberOfTracks() {
        return mNumberOfTracks;
    }
}
