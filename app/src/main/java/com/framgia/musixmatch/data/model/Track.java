package com.framgia.musixmatch.data.model;

public abstract class Track {
    protected int trackId;
    protected String trackName;
    protected int albumId;
    protected String albumName;
    protected int artistId;
    protected String artistName;

    public Track(int trackId, String trackName, int albumId, String albumName, int artistId, String artistName) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public int getTrackId() {
        return trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }
}
