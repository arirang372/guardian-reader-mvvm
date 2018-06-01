package com.john.guardian.db.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Edition
{
    @SerializedName("id")
    @Expose
    public String id;//sectionId

    @SerializedName("webTitle")
    @Expose
    public String webTitle;

    @SerializedName("webUrl")
    @Expose
    public String webUrl;

    @SerializedName("apiUrl")
    @Expose
    public String apiUrl;

    @SerializedName("code")
    @Expose
    public String code;
}
