package com.sunasterisk.musixmatch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Track implements Parcelable {
    private int mTrackId;
    private String mTrackName;
    private int mAlbumId;
    private String mAlbumName;
    private int mArtistId;
    private String mArtistName;
    private String mData;
    private long mDuration;
    private long mSize;
    private String mLyrics;
    private String mTrackArt;

    public Track(Builder builder) {
        mTrackId = builder.mTrackId;
        mTrackName = builder.mTrackName;
        mAlbumId = builder.mAlbumId;
        mAlbumName = builder.mAlbumName;
        mArtistId = builder.mArtistId;
        mArtistName = builder.mArtistName;
        mData = builder.mData;
        mDuration = builder.mDuration;
        mSize = builder.mSize;
        mLyrics = builder.mLyrics;
        mTrackArt = builder.mTrackArt;
    }

    public Track(JSONObject jsonObject) throws JSONException {
        mTrackId = jsonObject.getInt(JSonKey.TRACK_ID);
        mTrackName = jsonObject.getString(JSonKey.TRACK_NAME);
        mAlbumId = jsonObject.getInt(JSonKey.ALBUM_ID);
        mAlbumName = jsonObject.getString(JSonKey.ARTIST_NAME);
        mArtistName = jsonObject.getString(JSonKey.ARTIST_NAME);
    }

    protected Track(Parcel in) {
        mTrackId = in.readInt();
        mTrackName = in.readString();
        mAlbumId = in.readInt();
        mAlbumName = in.readString();
        mArtistId = in.readInt();
        mArtistName = in.readString();
        mData = in.readString();
        mDuration = in.readLong();
        mSize = in.readLong();
        mLyrics = in.readString();
        mTrackArt = in.readString();
    }

    public static final Parcelable.Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public int getTrackId() {
        return mTrackId;
    }

    public String getTrackName() {
        return mTrackName;
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

    public String getData() {
        return mData;
    }

    public long getDuration() {
        return mDuration;
    }

    public long getSize() {
        return mSize;
    }

    public String getLyrics() {
        return mLyrics;
    }

    public String getTrackArt() {
        return mTrackArt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mTrackId);
        parcel.writeString(mTrackName);
        parcel.writeInt(mAlbumId);
        parcel.writeString(mAlbumName);
        parcel.writeInt(mArtistId);
        parcel.writeString(mArtistName);
        parcel.writeString(mData);
        parcel.writeLong(mDuration);
        parcel.writeLong(mSize);
        parcel.writeString(mLyrics);
        parcel.writeString(mTrackArt);
    }

    public static class Builder {
        private int mTrackId;
        private String mTrackName;
        private int mAlbumId;
        private String mAlbumName;
        private int mArtistId;
        private String mArtistName;
        private String mData;
        private long mDuration;
        private long mSize;
        private String mLyrics;
        private String mTrackArt;

        public Builder() {
        }

        public Builder setTrackId(int trackId) {
            mTrackId = trackId;
            return this;
        }

        public Builder setTrackName(String trackName) {
            mTrackName = trackName;
            return this;
        }

        public Builder setAlbumId(int albumId) {
            mAlbumId = albumId;
            return this;
        }

        public Builder setAlbumName(String albumName) {
            mAlbumName = albumName;
            return this;
        }

        public Builder setArtistId(int artistId) {
            mArtistId = artistId;
            return this;
        }

        public Builder setArtistName(String artistName) {
            mArtistName = artistName;
            return this;
        }

        public Builder setData(String data) {
            mData = data;
            return this;
        }

        public Builder setDuration(long duration) {
            mDuration = duration;
            return this;
        }

        public Builder setSize(long size) {
            mSize = size;
            return this;
        }

        public Builder setLyrics(String lyrics) {
            mLyrics = lyrics;
            return this;
        }

        public Builder setTrackArt(String trackArt) {
            mTrackArt = trackArt;
            return this;
        }

        public Track build() {
            return new Track(this);
        }
    }

    public class JSonKey {
        public static final String TRACK_LIST = "track_list";
        public static final String MESSAGE = "message";
        public static final String BODY = "body";
        public static final String COMMON_TRACK_ID = "commontrack_id";
        public static final String TRACK_NAME = "track_name";
        public static final String TRACK_ID = "track_id";
        public static final String TITLE = "title";
        public static final String GENRE = "genre";
        public static final String ARTIST_NAME = "artist_name";
        public static final String ARTIST_ID = "artist_id";
        public static final String ALBUM_NAME = "album_name";
        public static final String ALBUM_ID = "album_id";
        public static final String TRACK = "track";
        public static final String LYRICS = "lyrics";
        public static final String LYRICS_BODY = "lyrics_body";
    }

}
