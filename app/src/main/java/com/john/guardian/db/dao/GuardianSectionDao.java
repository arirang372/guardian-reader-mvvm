package com.john.guardian.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.john.guardian.db.entity.GuardianSection;
import java.util.List;

@Dao
public interface GuardianSectionDao
{
    @Query("SELECT * FROM sections")
    LiveData<List<GuardianSection>> loadAllSections();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GuardianSection> sections);

    @Query("select * from sections where id = :sectionId")
    LiveData<GuardianSection> loadSection(String sectionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSection(GuardianSection section);

    @Query("delete from sections where id = :sectionId")
    void deleteSection(String sectionId);

}
