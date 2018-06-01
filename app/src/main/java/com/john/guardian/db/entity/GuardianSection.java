package com.john.guardian.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.john.guardian.model.IGuardianSection;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by john on 7/11/2017.
 */
@Entity(tableName = "sections", indices = @Index(value = {"sectionName"}, unique = true))
public class GuardianSection implements IGuardianSection
{
    @PrimaryKey(autoGenerate = true )
    @SerializedName("primary_id")
    @Expose
    private int id;//auto generated id by Room

    @SerializedName("id")
    @Expose
    private String sectionName;//sectionId

    @SerializedName("webTitle")
    @Expose
    private String webTitle;

    @SerializedName("webUrl")
    @Expose
    private String webUrl;

    @SerializedName("apiUrl")
    @Expose
    private String apiUrl;


    @Ignore
    @SerializedName("editions")
    @Expose
    private List<Edition> editions;

    @Override
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    @Override
    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public List<Edition> getEditions() {
        return editions;
    }

    public void setEditions(List<Edition> editions) {
        this.editions = editions;
    }
}
