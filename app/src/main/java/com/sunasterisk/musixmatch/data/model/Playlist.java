package com.sunasterisk.musixmatch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by superme198 on 14,April,2019
 * in Music_Match__1.
 */
public class Playlist implements Parcelable {

    private int mId;
    private String mName;
    private int mSongsCount;

    public Playlist(int id, String name, int songsCount) {
        mSongsCount = songsCount;
        mId = id;
        mName = name;
    }

    protected Playlist(Parcel in) {
        mSongsCount = in.readInt();
        mId = in.readInt();
        mName = in.readString();
    }

    public static final Creator<Playlist> CREATOR = new Creator<Playlist>() {
        @Override
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        @Override
        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeInt(mSongsCount);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getSongsCount() {
        return mSongsCount;
    }

    public void setSongsCount(int songsCount) {
        mSongsCount = songsCount;
    }
}
