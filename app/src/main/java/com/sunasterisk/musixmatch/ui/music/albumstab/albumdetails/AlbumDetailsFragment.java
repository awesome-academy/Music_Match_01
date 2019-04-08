package com.sunasterisk.musixmatch.ui.music.albumstab.albumdetails;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.repository.TrackRepository;
import com.sunasterisk.musixmatch.data.source.local.TrackLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseFragment;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;
import com.sunasterisk.musixmatch.ui.music.trackstab.TracksTabAdapter;

import java.util.List;

import static com.sunasterisk.musixmatch.utils.Constants.ARGUMENT_ALBUM_ITEM;

/**
 * Created by superme198 on 05,April,2019
 * in Music_Match__1.
 */
public class AlbumDetailsFragment extends BaseFragment implements AlbumDetailsContract.View,
        OnRecyclerItemClickListener<Track>, View.OnClickListener {

    private Album mAlbum = new Album(new Album.Builder());
    private TracksTabAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private AlbumDetailsPresenter mPresenter;
    private CardView mAlbumArt;
    private ImageView mBackButton;
    private ImageView mMoreOptionButton;
    private CardView mShuffleButton;
    private TextView mAlbumName;
    private TextView mCurrentSongName;
    private TextView mNumsSong;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_album_details;
    }

    @Override
    protected void initComponents(View view) {
        mShuffleButton = view.findViewById(R.id.card_shuffle_play);
        mBackButton = view.findViewById(R.id.back_album_details);
        mMoreOptionButton = view.findViewById(R.id.more_option_album_details);
        mAlbumArt = view.findViewById(R.id.card_album_details_art);
        mRecyclerView = view.findViewById(R.id.recycler_tracks_tab);
        mAlbumName = view.findViewById(R.id.text_title_album);
        mCurrentSongName = view.findViewById(R.id.text_current_song);
        mNumsSong = view.findViewById(R.id.text_num_songs);
        mBackButton.setOnClickListener(this);
        mMoreOptionButton.setOnClickListener(this);
        mShuffleButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAlbum = getArguments().getParcelable(ARGUMENT_ALBUM_ITEM);
        TrackRepository repository = TrackRepository.getInstance(
                TrackLocalDataSource.getInstance(getContext()));
        mPresenter = new AlbumDetailsPresenter(repository, this, mAlbum);
        mPresenter.getLocalTracks();
        setAlbumInfo();
    }

    @Override
    public void onItemClicked(Track item) {

    }

    @Override
    public void onItemClicked(long id) {

    }

    @Override
    public void showTracksFromAlbum(List<Track> tracks) {
        mAdapter = new TracksTabAdapter(getContext());
        mAdapter.setItems(tracks);
        mAdapter.setCallBack(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showError(Exception e) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_option_album_details:
                showOptionMenu(mMoreOptionButton);
                break;
            case R.id.back_album_details:
                break;
            case R.id.card_shuffle_play:
                break;
        }
    }

    private void setAlbumInfo() {
        mAlbumName.setText(mAlbum.getAlbumName());
        mCurrentSongName.setText(mAlbum.getNumberOfSongs());
        mNumsSong.setText(mAlbum.getNumberOfSongs());
        mAlbumArt.setForeground(Drawable.createFromPath(mAlbum.getAlbumArt()));
    }

    private void showOptionMenu(View anchor) {
        PopupMenu popup = new PopupMenu(anchor.getContext(), anchor);
        popup.inflate(R.menu.options_menu_albums_tab);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.play:
                    return true;
                case R.id.add_to_queue:
                    return true;
                case R.id.add_to_playlist:
                    return true;
                case R.id.edit_info:
                    return true;
                default:
                    return false;
            }
        });
        popup.show();
    }
}
