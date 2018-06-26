package com.john.guardian.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import com.john.guardian.GuardianApplication;
import com.john.guardian.db.DataRepository;
import com.john.guardian.db.entity.GuardianContent;


public class ContentViewModel extends AndroidViewModel
{
    private LiveData<GuardianContent> contentLiveData = new MutableLiveData<>();
    private final int contentId;
    private final DataRepository repository;
    public ContentViewModel(@NonNull Application application, final int contentId)
    {
        super(application);
        this.contentId = contentId;
        repository = ((GuardianApplication) application).getRepository();
        contentLiveData = repository.getContent(contentId);
    }

    public LiveData<GuardianContent> getObservableContent()
    {
        return this.contentLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory
    {
        @NonNull
        private final Application mApplication;
        private final int contentId;

        public Factory(@NonNull Application application, int contentId)
        {
            this.mApplication = application;
            this.contentId = contentId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass)
        {
            return (T) new ContentViewModel(mApplication, contentId);
        }
    }
}
