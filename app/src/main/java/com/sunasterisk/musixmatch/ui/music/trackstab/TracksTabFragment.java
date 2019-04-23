package com.sunasterisk.musixmatch.ui.music.trackstab;

import android.support.v7.widget.PopupMenu;
import android.view.View;

import com.sunasterisk.musixmatch.R;
import com.sunasterisk.musixmatch.data.model.Track;
import com.sunasterisk.musixmatch.data.repository.TrackRepository;
import com.sunasterisk.musixmatch.data.source.local.TrackLocalDataSource;
import com.sunasterisk.musixmatch.ui.base.OnRecyclerItemClickListener;
import com.sunasterisk.musixmatch.ui.playing.tracks.TracksFragment;

import java.util.List;

/**
 * Created by superme198 on 01,April,2019
 * in Music_Match__1.
 */
public class TracksTabFragment extends TracksFragment implements OnRecyclerItemClickListener<Track> {

    protected TracksTabAdapter mAdapter;

    public static TracksTabFragment newInstance() {
        return new TracksTabFragment();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_tracks_tab;
    }

    @Override
    protected void initData() {
        mPresenter = new TracksTabPresenter(
                TrackRepository.getInstance(TrackLocalDataSource.getInstance(getActivity())),
                this);
        mPresenter.getLocalTracks();
    }

    @Override
    public void initComponents(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_tracks_tab);
    }

    @Override
    public void showLocalTracks(List<Track> tracks) {
        mAdapter = new TracksTabAdapter(getContext());
        mAdapter.setCallBack(this);
        mAdapter.setItems(tracks);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClicked(View view, long pos, Track item) {
        switch (view.getId()) {
            case R.id.button_more:
                showOptionMenu(view, item);
                break;
            default:
                break;
        }
    }

    private void showOptionMenu(View v, Track track) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.inflate(R.menu.options_menu_tracks_tab);
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
