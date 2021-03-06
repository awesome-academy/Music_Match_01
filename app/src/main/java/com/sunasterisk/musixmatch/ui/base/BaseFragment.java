package com.sunasterisk.musixmatch.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResource(), container, false);
        initComponents(rootView);
        initData();
        return rootView;
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected abstract void initComponents(View view);

    protected abstract void initData();
}
