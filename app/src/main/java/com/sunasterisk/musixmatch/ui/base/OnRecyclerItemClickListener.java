package com.sunasterisk.musixmatch.ui.base;

import android.view.View;

/**
 * Created by superme198 on 03,April,2019
 * in Music_Match__1.
 */
public interface OnRecyclerItemClickListener<T> extends BaseRecyclerListener {

    void onItemClicked(View view, long pos, T item);
}
