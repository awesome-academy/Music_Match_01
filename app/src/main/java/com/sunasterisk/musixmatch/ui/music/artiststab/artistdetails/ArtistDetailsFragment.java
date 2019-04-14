package com.sunasterisk.musixmatch.ui.music.artiststab.artistdetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.model.Artist;
import com.sunasterisk.musixmatch.data.repository.AlbumRepository;
import com.sunasterisk.musixmatch.data.source.local.AlbumLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseFragment;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;
import com.sunasterisk.musixmatch.ui.music.artiststab.ArtistsTabAdapter;
import com.sunasterisk.musixmatch.ui.music.artiststab.ArtistsTabFragment;

import java.util.List;

/**
 * Created by superme198 on 13,April,2019
 * in Music_Match__1.
 */
public class ArtistDetailsFragment extends BaseFragment implements ArtistDetailsContract.View,
        OnRecyclerItemClickListener<Album>, View.OnClickListener {

    private Artist mArtist = new Artist(new Artist.Builder());
    private ArtistDetailsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArtistDetailsContract.Presenter mPresenter;
    private ImageView mBackButton;
    private ImageView mOptionsMore;
    private TextView mArtistTitle;

    public static ArtistDetailsFragment newInstance() {
        return new ArtistDetailsFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.framgent_artist_details;
    }

    @Override
    protected void initComponents(View view) {
        mOptionsMore = view.findViewById(R.id.more_option_artist_details);
        mBackButton = view.findViewById(R.id.back_artist_details);
        mArtistTitle = view.findViewById(R.id.text_artist_name);
        mRecyclerView = view.findViewById(R.id.recycler_tracks_tab);
        view.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mArtist = getArguments().getParcelable(ArtistsTabAdapter.ARGUMENT_ARTIST_ITEM);
        AlbumRepository repository = AlbumRepository.getInstance(AlbumLocalDataSource.getInstance(getContext()));
        mPresenter = new ArtistDetailsPresenter(repository, this);
        mPresenter.getAlbums(mArtist.getArtistId());
        showArtistInfo(mArtist);
    }

    @Override
    public void showAlbums(List<Album> albums) {
        mAdapter = new ArtistDetailsAdapter(getContext(), getFragmentManager());
        mAdapter.setCallBack(this);
        mAdapter.setItems(albums);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showError(Exception e) {

    }

    @Override
    public void onItemClicked(Album item) {

    }

    @Override
    public void onItemClicked(Album item, List<Album> items) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_artist_details:
                System.out.println("back");
                break;
            case R.id.more_option_artist_details:
                System.out.println("more");
                break;
        }
    }

    public void showArtistInfo(Artist artist){
        mArtistTitle.setText(artist.getArtistName());
    }
}
