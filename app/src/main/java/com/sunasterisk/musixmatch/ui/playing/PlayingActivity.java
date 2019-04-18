package com.sunasterisk.musixmatch.ui.playing;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Album;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.service.LoopMode;
import com.sunasterisk.musixmatch.service.MediaListener;
import com.sunasterisk.musixmatch.service.MusicService;
import com.sunasterisk.musixmatch.service.OnMediaPlayerChangeListener;
import com.sunasterisk.musixmatch.ui.base.BaseActivity;
import com.sunasterisk.musixmatch.ui.playing.lyrics.LyricsFragment;
import com.sunasterisk.musixmatch.ui.playing.thumbnail.BottomSheetMenuFragment;
import com.sunasterisk.musixmatch.ui.playing.thumbnail.ThumbnailFragment;
import com.sunasterisk.musixmatch.ui.playing.tracks.TracksFragment;
import com.sunasterisk.musixmatch.ui.track_info.TrackInfoActivity;
import com.sunasterisk.musixmatch.utils.Constants;
import com.sunasterisk.musixmatch.utils.StringUtils;
import com.sunasterisk.musixmatch.utils.widget.RepeatButtonView;

import java.util.List;

public class PlayingActivity extends BaseActivity implements TracksFragment.OnGetTracksListener,
        ThumbnailFragment.OnGetAlbumsListener, OnMediaPlayerChangeListener, View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, BottomSheetMenuFragment.OnMenuClickListener {
    private static final String EXTRA_TRACK_NAME =
            "com.sunasterisk.musixmatch.ui.playing.EXTRA_TRACK_NAME";
    private static final String EXTRA_ARTIST_NAME =
            "com.sunasterisk.musixmatch.ui.playing.EXTRA_ARTIST_NAME";
    private static final int TIME_DELAY = 1000;
    private static final int REQUEST_TRACK_INFO = 186;
    private TextView mTextTrackName;
    private TextView mTextArtistName;
    private TextView mTextCurrentTime;
    private TextView mTextTotalTime;
    private SeekBar mSeekBarProgressTime;
    private RepeatButtonView mButtonRepeat;
    private ImageButton mButtonBack;
    private ImageButton mButtonPlay;
    private ImageButton mButtonNext;
    private ImageButton mButtonFavorite;
    private ViewPager mViewPager;
    private TabLayout mTabDots;
    private PlayingViewPagerAdapter mPagerAdapter;
    private List<Album> mAlbums;
    private List<Track> mTracks;
    private Track mTrack;
    private int mPosition;
    private MusicService mMusicService;
    private MediaListener mMediaListener;
    private Handler mHandler;
    private Runnable mRunnable;
    private int mLoopState;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder boundService = (MusicService.MusicBinder) service;
            mMusicService = boundService.getService();
            mMediaListener = mMusicService.getMediaListener();
            mMusicService.setOnMediaPlayerChangeListener(PlayingActivity.this);
            mLoopState = mMediaListener.getStateLoop();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public static Intent getIntentPlaying(String trackName, String artistName) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TRACK_NAME, trackName);
        intent.putExtra(EXTRA_ARTIST_NAME, artistName);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_playing;
    }

    @Override
    protected void initComponents() {
        mTextTrackName = findViewById(R.id.text_track_name);
        mTextArtistName = findViewById(R.id.text_artist_name);
        mTextCurrentTime = findViewById(R.id.text_current_time);
        mTextTotalTime = findViewById(R.id.text_total_time);
        mSeekBarProgressTime = findViewById(R.id.seek_bar_progress_time);
        mButtonRepeat = findViewById(R.id.button_repeat);
        mButtonBack = findViewById(R.id.button_back);
        mButtonPlay = findViewById(R.id.button_play);
        mButtonNext = findViewById(R.id.button_next);
        mButtonFavorite = findViewById(R.id.button_favorite);
        mViewPager = findViewById(R.id.view_pager);
        mTabDots = findViewById(R.id.tab_dots);
        mPagerAdapter = new PlayingViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(PlayingViewPagerAdapter.THUMB_NAIL);
        mTabDots.setupWithViewPager(mViewPager);
        setEventListener();
    }

    private void setEventListener() {
        mButtonRepeat.setOnClickListener(this);
        mButtonFavorite.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonPlay.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
        mSeekBarProgressTime.setOnSeekBarChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void initData() {
        mButtonRepeat.setButtonState(mLoopState);
    }

    @Override
    public void onPlayed(Track track) {
        mPosition = mTracks.indexOf(track);
        startService(MusicService.getIntentService(this, mTracks, mPosition));
        mMediaListener.play(track.getData());
        onTrackPlayed(track);
        setTrackData(track);
    }

    @Override
    public void onGetTracksSuccess(List<Track> tracks) {
        mTracks = tracks;
        startService(MusicService.getIntentService(this, tracks, mPosition));
    }

    @Override
    public void onGetAlbumsSuccess(List<Album> albums) {
        mAlbums = albums;
    }

    @Override
    public void onMediaStateChange(boolean isPlaying) {
        mButtonPlay.setSelected(isPlaying);
    }

    @Override
    public void onLoopStateChange(int state) {
        switch (state) {
            case LoopMode.LOOP_OFF:
                mButtonRepeat.setButtonState(RepeatButtonView.STATE_REPEAT_OFF);
                break;
            case LoopMode.LOOP_ONE:
                mButtonRepeat.setButtonState(RepeatButtonView.STATE_REPEAT_ONE);
                break;
            case LoopMode.LOOP_ALL:
                mButtonRepeat.setButtonState(RepeatButtonView.STATE_REPEAT_ALL);
                break;
            case LoopMode.SHUFFLE_ON:
                mButtonRepeat.setButtonState(RepeatButtonView.STATE_SHUFFLE);
                break;
        }
    }

    @Override
    public void onTrackChange(Track track) {
        onTrackPlayed(track);
        setTrackData(track);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                mMediaListener.previous();
                break;
            case R.id.button_play:
                if (mMediaListener.isPlaying()) {
                    mMediaListener.pause();
                } else {
                    mMediaListener.start();
                }
                break;
            case R.id.button_next:
                mMediaListener.next();
                break;
            case R.id.button_repeat:
                mLoopState++;
                if (mLoopState > LoopMode.SHUFFLE_ON) {
                    mLoopState = LoopMode.LOOP_OFF;
                }
                mMediaListener.setStateLoop(mLoopState);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mMediaListener.seek(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onMenuItemClicked(String action) {
        if (action.equals(BottomSheetMenuFragment.ACTION_TRACK_INFO)) {
            startActivityForResult(TrackInfoActivity.getTrackIntent(this, mTrack), REQUEST_TRACK_INFO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TRACK_INFO && resultCode == RESULT_OK) {
            mTextTrackName.setText(data.getStringExtra(EXTRA_TRACK_NAME));
            mTextArtistName.setText(data.getStringExtra(EXTRA_ARTIST_NAME));
            passTrackToTracksScreen();
        }
    }

    private void setTrackData(Track track) {
        if (track != null) {
            mTextTrackName.setText(track.getTrackName());
            mTextArtistName.setText(track.getArtistName());
            if (track.getDuration() != 0) {
                mTextTotalTime.setText(StringUtils.convertMilliSeconds(track.getDuration()));
                mSeekBarProgressTime.setMax((int) track.getDuration());
            } else {
                mTextTotalTime.setText(
                        StringUtils.convertMilliSeconds(mMediaListener.getDuration()));
                mSeekBarProgressTime.setMax(mMediaListener.getDuration());
            }
            updateSeekBar();
        }
    }

    private void updateSeekBar() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                int currentDuration = mMediaListener.getCurrentDuration();
                mTextCurrentTime.setText(StringUtils.convertMilliSeconds(currentDuration));
                mSeekBarProgressTime.setProgress(currentDuration);
                mHandler.postDelayed(this, TIME_DELAY);
            }
        };
        mRunnable.run();
    }

    private void onTrackPlayed(Track track) {
        passTrackToThumbnailScreen(track);
        passTrackToLyricsScreen(track);
    }

    private void passTrackToTracksScreen() {
        Fragment tracksFragment = getSupportFragmentManager()
                .findFragmentByTag(Constants.FragmentTag.TRACKS);
        if (tracksFragment != null && tracksFragment instanceof TracksFragment) {
            ((TracksFragment) tracksFragment).onTrackInfoUpdated();
        }
    }

    private void passTrackToThumbnailScreen(Track track) {
        Fragment thumbnailFragment = getSupportFragmentManager()
                .findFragmentByTag(Constants.FragmentTag.THUMB_NAIL);
        if (thumbnailFragment != null && thumbnailFragment instanceof ThumbnailFragment) {
            ((ThumbnailFragment) thumbnailFragment).getTrack(track);
            for (Album album : mAlbums) {
                if (album.getAlbumId() == track.getAlbumId()) {
                    ((ThumbnailFragment) thumbnailFragment).setImageTrack(album.getAlbumArt());
                    mTrack = new Track.Builder()
                            .setTrackId(track.getTrackId())
                            .setTrackName(track.getTrackName())
                            .setTrackArt(album.getAlbumArt())
                            .setArtistId(track.getArtistId())
                            .setArtistName(track.getArtistName())
                            .setAlbumId(track.getAlbumId())
                            .setAlbumName(track.getAlbumName())
                            .setDuration(track.getDuration())
                            .setData(track.getData())
                            .setSize(track.getSize())
                            .build();
                }
            }
        }
    }

    private void passTrackToLyricsScreen(Track track) {
        Fragment lyricsFragment = getSupportFragmentManager()
                .findFragmentByTag(Constants.FragmentTag.LYRICS);
        if (lyricsFragment != null && lyricsFragment instanceof LyricsFragment) {
            ((LyricsFragment) lyricsFragment).getLyrics(track);
        }
    }
}
