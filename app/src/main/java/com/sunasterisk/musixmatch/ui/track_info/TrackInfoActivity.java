package com.sunasterisk.musixmatch.ui.track_info;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.repository.TrackRepository;
import com.sunasterisk.musixmatch.data.source.local.TrackLocalDataSource;
import com.sunasterisk.musixmatch.data.source.remote.TrackRemoteDataSource;
import com.sunasterisk.musixmatch.ui.base.BaseActivity;
import com.sunasterisk.musixmatch.ui.playing.PlayingActivity;
import com.sunasterisk.musixmatch.utils.StringUtils;

public class TrackInfoActivity extends BaseActivity
        implements TrackInfoContract.View, View.OnClickListener {
    public static final String EXTRA_TRACK =
            "com.sunasterisk.musixmatch.ui.track_info.EXTRA_TRACK";
    private TextView mTextTrackName;
    private EditText mTextTrackTitle;
    private EditText mTextArtists;
    private EditText mTextAlbums;
    private TextView mTextSize;
    private TextView mTextPath;
    private ImageButton mButtonClose;
    private ImageButton mButtonConfirm;
    private ImageView mImageTrack;
    private Track mTrack;
    private TrackInfoContract.Presenter mPresenter;

    public static Intent getTrackIntent(Context context, Track track) {
        Intent intent = new Intent(context, TrackInfoActivity.class);
        intent.putExtra(EXTRA_TRACK, track);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_track_info;
    }

    @Override
    protected void initComponents() {
        mTextTrackName = findViewById(R.id.text_track_name);
        mTextTrackTitle = findViewById(R.id.text_title);
        mTextArtists = findViewById(R.id.text_artists);
        mTextAlbums = findViewById(R.id.text_albums);
        mTextSize = findViewById(R.id.text_size);
        mTextPath = findViewById(R.id.text_path);
        mButtonClose = findViewById(R.id.button_close);
        mButtonConfirm = findViewById(R.id.button_confirm);
        mImageTrack = findViewById(R.id.image_track);
        mButtonClose.setOnClickListener(this);
        mButtonConfirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Track track = getIntent().getParcelableExtra(EXTRA_TRACK);
        mTrack = track;
        mPresenter = new TrackInfoPresenter(TrackRepository.getInstance(
                TrackLocalDataSource.getInstance(this),
                TrackRemoteDataSource.getInstance()),
                this);
        mPresenter.getTrackInfo(track);
    }

    @Override
    public void showTrackInfo(Track track) {
        if (track != null) {
            mTextTrackName.setText(track.getTrackName());
            mTextTrackTitle.setText(track.getTrackName());
            mTextArtists.setText(track.getArtistName());
            mTextAlbums.setText(track.getAlbumName());
            mTextSize.setText(getString(R.string.text_size) + StringUtils.convertSize(track.getSize()));
            mTextPath.setText(getString(R.string.text_path) + track.getData());
            mImageTrack.setImageDrawable(Drawable.createFromPath(track.getTrackArt()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_close:
                finish();
                break;
            case R.id.button_confirm:
                updateTrackInfo();
                break;
        }
    }

    private void updateTrackInfo() {
        if (!StringUtils.isEmpty(this, mTextTrackTitle, mTextArtists, mTextAlbums)) {
            String trackTitle = mTextTrackTitle.getText().toString();
            String artistName = mTextArtists.getText().toString();
            String albumName = mTextAlbums.getText().toString();
            mPresenter.updateTrackInfo(mTrack.getTrackId(),
                    trackTitle, artistName, albumName);
            setResult(RESULT_OK, PlayingActivity.getIntentPlaying(trackTitle, artistName));
            finish();
        }
    }
}
