package com.sunasterisk.musixmatch.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunasterisk.musixmatch.R;

/**
 * Created by superme198 on 04,April,2019
 * in Music_Match__1.
 */
public abstract class BaseTrackViewHolder<T, L extends BaseRecyclerListener> extends BaseViewHolder<T, L> {

    protected ImageView mOptionMore;
    protected TextView mTextTitle;
    protected TextView mTextSubTitle;

    public BaseTrackViewHolder(Context context, @NonNull View itemView, L callback) {
        super(context, itemView, callback);
        mTextTitle = itemView.findViewById(R.id.text_title);
        mTextSubTitle = itemView.findViewById(R.id.text_subtitle);
        mOptionMore = itemView.findViewById(R.id.button_more);
    }

}
