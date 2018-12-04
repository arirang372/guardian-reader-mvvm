package com.john.guardian.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import com.john.guardian.AppExecutors;
import com.john.guardian.db.dao.GuardianContentDao;
import com.john.guardian.db.dao.GuardianSectionDao;
import com.john.guardian.db.entity.GuardianContent;
import com.john.guardian.db.entity.GuardianSection;

@Database(entities = {GuardianSection.class, GuardianContent.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase
{
    private static AppDatabase instance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "GuardianDatabase";

    public abstract GuardianSectionDao sectionDao();

    public abstract GuardianContentDao contentDao();


    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors)
    {
        synchronized (AppDatabase.class)
        {
            if(instance == null)
            {
                instance = createDatabase(context, executors);
                instance.updateDatabaseCreated(context.getApplicationContext());
            }
        }
        return instance;
    }

    private static AppDatabase createDatabase(final Context context, final AppExecutors executors)
    {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db)
                        {
                            super.onCreate(db);
                            executors.diskIO().execute(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                   // addDelay();
                                    AppDatabase database = AppDatabase.getInstance(context, executors);
                                    database.setDatabaseCreated();
                                }
                            });
                        }
                    }).build();
    }

//    private static void addDelay() {
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException ignored) {
//        }
//    }

    private void updateDatabaseCreated(final Context context)
    {
        if(context.getDatabasePath(DATABASE_NAME).exists())
        {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated()
    {
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated()
    {
        return this.isDatabaseCreated;
    }
}
