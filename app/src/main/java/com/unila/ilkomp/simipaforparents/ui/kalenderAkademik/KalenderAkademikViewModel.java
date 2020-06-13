package com.unila.ilkomp.simipaforparents.ui.kalenderAkademik;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KalenderAkademikViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public KalenderAkademikViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}