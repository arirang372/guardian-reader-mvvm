package com.john.guardian.db.rest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.TypeConverters;
import android.os.Looper;

import com.john.guardian.db.AppDatabase;
import com.john.guardian.db.converter.DateConverter;
import com.john.guardian.db.entity.GuardianContent;
import com.john.guardian.db.entity.GuardianSection;
import com.john.guardian.db.rest.models.contents.GuardianContentResponse;
import com.john.guardian.db.rest.models.contents.HttpContentResponse;
import com.john.guardian.db.rest.models.sections.GuardianSectionResponse;
import com.john.guardian.db.rest.models.sections.HttpSectionResponse;
import com.john.guardian.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.john.guardian.utils.LogUtils.LOGD;


@TypeConverters(DateConverter.class)
public class DataLoader
{
    private static final String BASE_URL = "https://content.guardianapis.com";
    private final String TAG = this.getClass().getSimpleName();
    private GuadianService service;
    private Utils utils = Utils.getInstance();
    private String apiKey;
    private AppDatabase database;

    public DataLoader(AppDatabase database)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(GuadianService.class);
        this.database = database;
    }

    public MutableLiveData<List<GuardianSection>> loadNewsSections(String apiKey)
    {
        this.apiKey = apiKey;
        final MutableLiveData<List<GuardianSection>> sections = new MutableLiveData<>();
        service.getSectionNames("", apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<HttpSectionResponse, GuardianSectionResponse>()
                {
                    @Override
                    public GuardianSectionResponse apply(HttpSectionResponse httpSectionResponse) throws Exception
                    {
                        return httpSectionResponse.response;
                    }
                })
                .flatMap(new Function<GuardianSectionResponse, Observable<List<GuardianSection>>>()
                {
                    @Override
                    public Observable<List<GuardianSection>> apply(GuardianSectionResponse guardianSectionResponse) {
                        return Observable.just(guardianSectionResponse.results);
                    }
                })
                .subscribe(new Observer<List<GuardianSection>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GuardianSection> guardianSections)
                    {
                        LOGD(TAG, "Success - Section received ...");
                        sections.setValue(guardianSections);

//                        new Thread(new Runnable() {
//                            @Override
//                            public void run()
//                            {
                               // processNewsSections(guardianSections);
//                            }
//                        }).start();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        LOGD(TAG, "Fail - error occurred ...");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return sections;
    }

    private void processNewsSections(final List<GuardianSection> sections)
    {
        if(sections.isEmpty())
            return;

        database.sectionDao().insertAll(sections);
    }


    public void loadNewsContents(String sectionId, String apiKey, BehaviorSubject<Boolean> networkLoading)
    {
        this.apiKey = apiKey;
        loadNextContents(sectionId);
    }

    private void loadNextContents(final String sectionId)
    {
        service.getNewsContents(sectionId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<HttpContentResponse, GuardianContentResponse>()
                {
                    @Override
                    public GuardianContentResponse apply(HttpContentResponse httpContentResponse)
                    {
                        return httpContentResponse.response;
                    }
                })
                .flatMap(new Function<GuardianContentResponse, Observable<List<GuardianContent>>>()
                {
                    @Override
                    public Observable<List<GuardianContent>> apply(GuardianContentResponse guardianContentResponse)
                    {
                        return Observable.just(guardianContentResponse.results);
                    }
                })
                .subscribe(new Observer<List<GuardianContent>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GuardianContent> guardianContents) {
                        LOGD(TAG, String.format("Success - Data received : %s", sectionId) );
                       processNewsContents( guardianContents);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void processNewsContents( final List<GuardianContent> contents)
    {
        if(contents.isEmpty())
            return;

        for(GuardianContent c : contents)
        {
            c.setWebPublicationDate( utils.reformatDate(c.getWebPublicationDate()) );
        }

        database.contentDao().insertAll(contents);
    }
}
