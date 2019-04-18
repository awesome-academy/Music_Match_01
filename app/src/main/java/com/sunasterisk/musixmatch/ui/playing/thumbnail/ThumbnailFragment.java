package com.sunasterisk.musixmatch.ui.playing.thumbnail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.repository.AlbumRepository;
import com.sunasterisk.musixmatch.data.source.local.AlbumLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseFragment;

import java.util.List;

public class ThumbnailFragment extends BaseFragment
        implements ThumbnailContract.View, View.OnClickListener {
    private ImageView mImageTrack;
    private ImageButton mButtonAdd;
    private ImageButton mButtonMore;
    private ThumbnailContract.Presenter mPresenter;
    private OnGetAlbumsListener mCallback;
    private Track mTrack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnGetAlbumsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnGetAlbumsListener");
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_thumbnail;
    }

    @Override
    protected void initComponents(View view) {
        mImageTrack = view.findViewById(R.id.image_track);
        mButtonAdd = view.findViewById(R.id.button_add);
        mButtonMore = view.findViewById(R.id.button_more);
        mButtonAdd.setOnClickListener(this);
        mButtonMore.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mPresenter = new ThumbnailPresenter(
                AlbumRepository.getInstance(AlbumLocalDataSource.getInstance(getActivity())),
                this);
        mPresenter.getAlbums();
    }

    public void setImageTrack(String albumArt) {
        if (albumArt != null) {
            mImageTrack.setImageDrawable(Drawable.createFromPath(albumArt));
        }
    }

    @Override
    public void showAlbums(List<Album> albums) {
        mCallback.onGetAlbumsSuccess(albums);
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallback = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                break;
            case R.id.button_more:
                showMenuMore();
                break;
        }
    }

    public void getTrack(Track track) {
        mTrack = track;
    }

    private void showMenuMore() {
        BottomSheetMenuFragment fragment = (BottomSheetMenuFragment)
                BottomSheetMenuFragment.newInstance(R.layout.fragment_dialog_more);
        fragment.show(getChildFragmentManager(), BottomSheetMenuFragment.class.getSimpleName());
    }

    public interface OnGetAlbumsListener {
        void onGetAlbumsSuccess(List<Album> albums);
    }
}
