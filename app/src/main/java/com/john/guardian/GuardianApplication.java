package com.john.guardian;

import android.app.Application;

import com.john.guardian.db.AppDatabase;
import com.john.guardian.db.DataRepository;

public class GuardianApplication extends Application
{
    private AppExecutors executors;

    @Override
    public void onCreate()
    {
        super.onCreate();

        executors = new AppExecutors();
    }

    public AppDatabase getDatabase()
    {
        return AppDatabase.getInstance(this, executors);
    }

    public DataRepository getRepository()
    {
        return DataRepository.getInstance(getDatabase());
    }

}
