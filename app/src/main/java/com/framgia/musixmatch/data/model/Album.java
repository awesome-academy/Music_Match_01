package com.framgia.musixmatch.data.model;

public class Album {
    private int mAlbumId;
    private String mAlbumName;
    private int mArtistId;
    private String mArtistName;
    private int mNumSOngs;
    private String mAlbumArt;

    public Album(int albumId, String albumName, int artistId, String artistName, int numSOngs, String albumArt) {
        mAlbumId = albumId;
        mAlbumName = albumName;
        mArtistId = artistId;
        mArtistName = artistName;
        mNumSOngs = numSOngs;
        mAlbumArt = albumArt;
    }

    public int getAlbumId() {
        return mAlbumId;
    }

    public String getAlbumName() {
        return mAlbumName;
    }

    public int getArtistId() {
        return mArtistId;
    }

    public String getArtistName() {
        return mArtistName;
    }

    public int getNumSOngs() {
        return mNumSOngs;
    }

    public String getAlbumArt() {
        return mAlbumArt;
    }
}
