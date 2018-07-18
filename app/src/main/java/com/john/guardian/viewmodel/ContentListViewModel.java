package com.john.guardian.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.john.guardian.GuardianApplication;
import com.john.guardian.db.DataRepository;
import com.john.guardian.db.entity.GuardianContent;

import java.util.List;

public class ContentListViewModel extends AndroidViewModel
{
    private final String sectionId;
    private DataRepository repository;
    private LiveData<List<GuardianContent>> mObservableContents;

    public ContentListViewModel(@NonNull Application application, final String sectionId)
    {
        super(application);
        this.sectionId = sectionId;
        this.repository = ((GuardianApplication) application).getRepository();
        this.mObservableContents = repository.loadAllContents(sectionId);
    }


    public LiveData<List<GuardianContent>> getObservableContents() {
        return mObservableContents;
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory
    {

        @NonNull
        private final Application mApplication;

        private final String mSectionId;

        public Factory(@NonNull Application application, String sectionId) {
            mApplication = application;
            mSectionId = sectionId;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ContentListViewModel(mApplication, mSectionId);
        }
    }
}
