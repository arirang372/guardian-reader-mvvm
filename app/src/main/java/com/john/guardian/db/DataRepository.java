package com.john.guardian.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import com.john.guardian.db.entity.GuardianContent;
import com.john.guardian.db.entity.GuardianSection;
import com.john.guardian.db.rest.DataLoader;

import java.util.List;

public class DataRepository
{
    private static DataRepository instance;
    private final AppDatabase database;
    private MediatorLiveData<List<GuardianSection>> observableSections;
    private DataLoader dataLoader;

    private DataRepository(final AppDatabase database)
    {
        this.database = database;
        observableSections = new MediatorLiveData<>();
        observableSections.addSource(database.sectionDao().loadAllSections(),
                new Observer<List<GuardianSection>>() {
                    @Override
                    public void onChanged(@Nullable List<GuardianSection> sections) {
                        if(database.getDatabaseCreated().getValue() != null)
                        {
                            observableSections.postValue(sections);
                        }
                    }
                });
        dataLoader = new DataLoader(database);
    }

    public static DataRepository getInstance(final AppDatabase database)
    {
        synchronized (DataRepository.class)
        {
            if (instance == null)
            {
                instance = new DataRepository(database);
            }

            return instance;
        }
    }

    public LiveData<List<GuardianSection>> getSections()
    {
        return this.observableSections;
    }

    public LiveData<List<GuardianSection>> loadAllSections()
    {
        return dataLoader.loadNewsSections("b71a5ddb-e819-4864-8006-944f614834b3");
    }

    public LiveData<GuardianSection> loadSection(final String sectionId)
    {
        return database.sectionDao().loadSection(sectionId);
    }

    public LiveData<List<GuardianContent>> loadContents(final String sectionId)
    {
        return database.contentDao().loadContents(sectionId);
    }

    public void insertContent(final GuardianContent newContent)
    {
        database.contentDao().insertContent(newContent);
    }

    public void deleteContent(String contentId)
    {
        database.contentDao().deleteContent(contentId);
    }

}
