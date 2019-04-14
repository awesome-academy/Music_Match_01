package com.sunasterisk.musixmatch.ui.base;

import java.util.List;

/**
 * Created by superme198 on 03,April,2019
 * in Music_Match__1.
 */
public interface OnRecyclerItemClickListener<T> extends BaseRecyclerListener {
    void onItemClicked(T item);
    void onItemClicked(T item, List<T> items);
}
