package com.sunasterisk.musixmatch.ui.music.artiststab.artistdetails;

import com.sunasterisk.musixmatch.data.model.Album;

import java.util.List;

/**
 * Created by superme198 on 13,April,2019
 * in Music_Match__1.
 */
public interface ArtistDetailsContract {

    interface View {
        void showAlbums(List<Album> albums);

        void showError(Exception e);
    }

    interface Presenter {

        void getAlbums(int artistId);
    }
}
