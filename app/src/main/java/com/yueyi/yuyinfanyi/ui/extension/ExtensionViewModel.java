package com.yueyi.yuyinfanyi.ui.extension;

import android.app.Application;

import com.yueyi.yuyinfanyi.base.MyBaseViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

public class ExtensionViewModel extends MyBaseViewModel {
    public ObservableField<String> target_language_text = new ObservableField<>("翻译结果");
    public ExtensionViewModel(@NonNull Application application) {
        super(application);
    }

}
