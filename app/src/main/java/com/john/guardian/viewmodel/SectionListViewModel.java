package com.john.guardian.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import com.john.guardian.GuardianApplication;
import com.john.guardian.db.DataRepository;
import com.john.guardian.db.entity.GuardianSection;
import java.util.List;

public class SectionListViewModel extends AndroidViewModel
{
    private DataRepository repository;
    private final MediatorLiveData<List<GuardianSection>> observableSections;
    public SectionListViewModel(Application application)
    {
        super(application);
        observableSections = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableSections.setValue(null);

        repository = ((GuardianApplication) application).getRepository();

        observableSections.addSource(repository.loadAllSections(), new Observer<List<GuardianSection>>() {
            @Override
            public void onChanged(@Nullable List<GuardianSection> sections) {
                observableSections.setValue(sections);
            }
        });

       // observableSections = repository.loadAllSections();
    }

    public LiveData<List<GuardianSection>> getSections()
    {
        return observableSections;
    }
}
