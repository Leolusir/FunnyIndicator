package com.leo.funnyindicator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leo.funnyindicator.R;

/**
 * Created by littleming on 15/7/29.
 */
public class PagerItem extends Fragment{
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.pager_item, container, false);
        }else{
            ViewGroup parent = (ViewGroup)rootView.getParent();
            if(parent != null){
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

}
