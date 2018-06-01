package com.john.guardian.db.rest.models.sections;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.john.guardian.db.entity.GuardianSection;
import java.util.List;

/**
 * Created by john on 7/11/2017.
 */

public class GuardianSectionResponse
{
    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("userTier")
    @Expose
    public String userTier;

    @SerializedName("total")
    @Expose
    public int total;

    @SerializedName("results")
    @Expose
    public List<GuardianSection> results;

}
