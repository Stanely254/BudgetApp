package com.stanley.budgetwatch.intro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.stanley.budgetwatch.fragment.IntroSlide1;
import com.stanley.budgetwatch.fragment.IntroSlide2;
import com.stanley.budgetwatch.fragment.IntroSlide3;
import com.stanley.budgetwatch.fragment.IntroSlide4;
import com.stanley.budgetwatch.fragment.IntroSlide5;
import com.stanley.budgetwatch.fragment.IntroSlide6;
import com.stanley.budgetwatch.fragment.IntroSlide7;

public class IntroActivity extends AppIntro {

    @Override
    public void init(Bundle savedInStanceState){
        addSlide(new IntroSlide1());
        addSlide(new IntroSlide2());
        addSlide(new IntroSlide3());
        addSlide(new IntroSlide4());
        addSlide(new IntroSlide5());
        addSlide(new IntroSlide6());
        addSlide(new IntroSlide7());
    }

    @Override
    public void onSkipPressed(Fragment fragment){
        finish();
    }

    @Override
    public void onDonePressed(Fragment fragment){
        finish();
    }
}
