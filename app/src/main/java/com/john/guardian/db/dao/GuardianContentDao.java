package com.john.guardian.db.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.john.guardian.db.entity.GuardianContent;

import java.util.List;

@Dao
public interface GuardianContentDao
{
    @Query("SELECT * FROM contents where sectionId = :sectionId")
    LiveData<List<GuardianContent>> loadContents(String sectionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GuardianContent> contents);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContent(GuardianContent content);


    @Query("delete from contents where id = :contentId")
    void deleteContent(String contentId);

}
