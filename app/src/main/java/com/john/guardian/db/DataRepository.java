package com.john.guardian.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;
import android.support.annotation.Nullable;
import com.john.guardian.R;
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
    private Context context;

    private DataRepository(final AppDatabase database, final Context context)
    {
        this.database = database;
        this.context = context;
        observableSections = new MediatorLiveData<>();
        observableSections.addSource(database.sectionDao().loadAllSections(),
                new android.arch.lifecycle.Observer<List<GuardianSection>>() {
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

    public static DataRepository getInstance(final AppDatabase database, final Context context)
    {
        synchronized (DataRepository.class)
        {
            if (instance == null)
            {
                instance = new DataRepository(database, context);
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
        return dataLoader.loadNewsSections(context.getString(R.string.api_key));
    }

    public LiveData<List<GuardianContent>> loadAllContents(String sectionId)
    {

        return dataLoader.loadNewsContents(sectionId, context.getString(R.string.api_key));
    }


    public LiveData<GuardianSection> loadSection(final String sectionId)
    {
        return database.sectionDao().loadSection(sectionId);
    }

    public LiveData<List<GuardianContent>> loadContents(final String sectionId)
    {
        return database.contentDao().loadContents(sectionId);
    }

    public LiveData<GuardianContent> getContent(final int id)
    {
        return database.contentDao().loadContent(id);
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
