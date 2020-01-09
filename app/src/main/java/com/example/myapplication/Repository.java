package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import io.reactivex.Observable;

public class Repository {

    private final MediaApi mediaApi;

    private static final int DEFAULT_PAGE_SIZE = 10;

    public Repository(MediaApi mediaApi) {
        this.mediaApi = mediaApi;
    }

    public Observable<PagedList<Media>> getPageListOfMedia(@NonNull final String userId) {

        // we have to create a new factory for each different request
        MediaDataSourceFactory mediaDataSourceFactory = new MediaDataSourceFactory(mediaApi, userId);

        PagedList.Config config = new PagedList.Config.Builder()
                .setMaxSize(100)
                .setPageSize(DEFAULT_PAGE_SIZE)
                .setInitialLoadSizeHint(DEFAULT_PAGE_SIZE)
                .build();

        return new RxPagedListBuilder(mediaDataSourceFactory, config)
                .buildObservable();
    }
}
