package com.stanley.budgetwatch.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.stanley.budgetwatch.R;

public class IntroSlide2  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.intro2_layout, container, false);
    }
}
