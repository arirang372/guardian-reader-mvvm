package com.john.guardian.model;

public interface IGuardianContent
{
    int getId();
    String getResourceName();
    String getType();
    String getSectionId();
    String getSectionName();
    String getWebPublicationDate();
    String getWebTitle();
    String getWebUrl();
    String getApiUrl();
    boolean getIsHosted();


}
