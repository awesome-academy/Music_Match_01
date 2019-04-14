package com.sunasterisk.musixmatch.ui.music.artiststab.artistdetails;

import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.data.repository.AlbumRepository;
import com.sunasterisk.musixmatch.data.source.Callback;

import java.util.List;

/**
 * Created by superme198 on 13,April,2019
 * in Music_Match__1.
 */
public class ArtistDetailsPresenter implements ArtistDetailsContract.Presenter {

    private ArtistDetailsContract.View mView;
    private AlbumRepository mRepository;

    public ArtistDetailsPresenter(AlbumRepository repository, ArtistDetailsContract.View view) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void getAlbums(int artistId) {
        mRepository.getAlbums(artistId, new Callback<List<Album>>() {
            @Override
            public void getDataSuccess(List<Album> data) {
                mView.showAlbums(data);
            }

            @Override
            public void getDataFailure(Exception e) {
                mView.showError(e);
            }
        });
    }
}
