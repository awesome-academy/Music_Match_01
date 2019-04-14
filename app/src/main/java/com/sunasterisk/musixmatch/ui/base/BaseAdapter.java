package com.sunasterisk.musixmatch.ui.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunasterisk.musixmatch.application.MusixMatchApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by superme198 on 03,April,2019
 * in Music_Match__1.
 */
public abstract class BaseAdapter<T, L extends BaseRecyclerListener,  VH extends BaseViewHolder<T, L>> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<T> mItems;
    protected L mCallback;
    protected LayoutInflater mLayoutInflater;
    protected static FragmentManager mFragmentManager;

    public BaseAdapter(Context context, FragmentManager fm){
        mContext = context;
        mFragmentManager = fm;
        mItems = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(MusixMatchApplication.getInstance());
    }

    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T item = mItems.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0: mItems.size();
    }

    /**
     * Inflates a view.
     *
     * @param layout       layout to me inflater
     * @param parent       container where to inflate
     * @param attachToRoot whether to attach to root or not
     * @return inflated View
     */
    @NonNull
    protected View inflate(@LayoutRes final int layout, @Nullable final ViewGroup parent, final boolean attachToRoot) {
        return mLayoutInflater.inflate(layout, parent, attachToRoot);
    }

    /**
     * Inflates a view.
     *
     * @param layout layout to me inflater
     * @param parent container where to inflate
     * @return inflated View
     */
    @NonNull
    protected View inflate(@LayoutRes final int layout, final @Nullable ViewGroup parent) {
        return inflate(layout, parent, false);
    }

    public void setItems(List<T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Cannot set `null` item to the Recycler adapter");
        }
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public List<T> getItems() {
        return mItems;
    }

    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null item to the Recycler adapter");
        }
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void remove(T item) {
        int position = mItems.indexOf(item);
        if (position > -1) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void setCallBack(L callBack){
        mCallback = callBack;
    }

    public L getCallBack(){
        return mCallback;
    }
}
