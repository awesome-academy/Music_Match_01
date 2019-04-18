package com.sunasterisk.musixmatch.ui.playing.thumbnail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunasterisk.musixmatch.R;

public class BottomSheetMenuFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String ACTION_TRACK_INFO =
            "com.sunasterisk.musixmatch.ui.playing.thumbnail.ACTION_TRACK_INFO";
    private static final String ARGUMENT_LAYOUT_ID = "ARG_LAYOUT_ID";
    private TextView mTextSongInfo;
    private OnMenuClickListener mCallback;

    public static BottomSheetDialogFragment newInstance(int layoutId) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_LAYOUT_ID, layoutId);
        BottomSheetMenuFragment fragment = new BottomSheetMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnMenuClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMenuClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                getArguments().getInt(ARGUMENT_LAYOUT_ID), container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        mTextSongInfo = view.findViewById(R.id.text_track_info);
        mTextSongInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mCallback.onMenuItemClicked(ACTION_TRACK_INFO);
        dismiss();
    }

    @StringDef({ACTION_TRACK_INFO})
    @interface Action {
    }

    public interface OnMenuClickListener {
        void onMenuItemClicked(@Action String action);
    }
}
