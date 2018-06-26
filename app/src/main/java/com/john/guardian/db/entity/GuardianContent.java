package com.john.guardian.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.john.guardian.model.IGuardianContent;

import java.io.Serializable;

import io.reactivex.annotations.NonNull;

/**
 * Created by john on 5/24/2018.
 */
@Entity(tableName = "contents",
        foreignKeys = {
            @ForeignKey(entity = GuardianSection.class,
                        parentColumns = "sectionName",
                        childColumns = "sectionId",
                        onDelete = ForeignKey.CASCADE) },
          indices = {@Index(value = "sectionId")})
public class GuardianContent implements IGuardianContent, Serializable
{
    @PrimaryKey
    @SerializedName("primary_id")
    @Expose
    private int id;

    @SerializedName("id")
    @Expose
    private String resourceName;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("sectionId")
    @Expose
    private String sectionId;

    @SerializedName("sectionName")
    @Expose
    private String sectionName;

    @SerializedName("webPublicationDate")
    @Expose
    private String webPublicationDate;

    @SerializedName("webTitle")
    @Expose
    private String webTitle;

    @SerializedName("webUrl")
    @Expose
    private String webUrl;

    @SerializedName("apiUrl")
    @Expose
    private String apiUrl;

    @SerializedName("isHosted")
    @Expose
    private boolean isHosted;

    private boolean isRead;

    public GuardianContent()
    {

    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
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
    public boolean getIsHosted() {
        return isHosted;
    }

    public void setIsHosted(boolean isHosted) {
        this.isHosted = isHosted;
    }

    public void setIsRead(boolean read)
    {
        this.isRead = read;
    }

    public boolean getIsRead()
    {
        return this.isRead;
    }

}
