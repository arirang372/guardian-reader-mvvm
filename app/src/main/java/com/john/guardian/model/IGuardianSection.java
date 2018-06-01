package com.john.guardian.model;

import com.john.guardian.db.entity.Edition;

import java.util.List;

public interface IGuardianSection
{
    int getId();
    String getSectionName();
    String getWebTitle();
    String getWebUrl();
    String getApiUrl();
    List<Edition> getEditions();

}

