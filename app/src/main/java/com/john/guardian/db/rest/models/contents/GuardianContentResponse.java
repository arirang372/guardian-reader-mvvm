package com.john.guardian.db.rest.models.contents;

import android.arch.lifecycle.MutableLiveData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.john.guardian.db.entity.GuardianContent;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;

/**
 * Created by john on 7/11/2017.
 */

public class GuardianContentResponse
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
    public List<GuardianContent> results;


    public Observable<List<GuardianContent>> getContents(MutableLiveData<List<GuardianContent>> contents)
    {
        return Observable.defer(new Callable<ObservableSource<? extends List<GuardianContent>>>() {
            @Override
            public ObservableSource<? extends List<GuardianContent>> call() throws Exception {
                if(results != null)
                    contents.setValue(results);

                return Observable.just(results);
            }
        });
    }

}
