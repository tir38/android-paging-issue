package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PageKeyedDataSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;

public class MediaDataSource extends ItemKeyedDataSource<String, Media> {

    private static final String NO_KEY = "no_key";

    private final MediaApi mediaApi;
    private final String userId;
    private Disposable getPageDisposable = Disposables.empty();

    MediaDataSource(final MediaApi mediaApi, final String userId) {
        this.mediaApi = mediaApi;
        this.userId = userId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<Media> callback) {
        Log.d("TAG", "loadInitial");
        String initialKey = params.requestedInitialKey == null ? NO_KEY : params.requestedInitialKey;
        int requestedLoadSize = params.requestedLoadSize;
        getPageDisposable.dispose();
        getPageDisposable = getMediasStartingAtKey(initialKey, requestedLoadSize)
                .subscribe(
                        callback::onResult,
                        Exceptions::propagate);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<Media> callback) {
        int requestedLoadSize = params.requestedLoadSize;
        String key = params.key;
        getPageDisposable.dispose();
        getPageDisposable = getMediasStartingAtKey(key, requestedLoadSize)
                .subscribe(
                        callback::onResult,
                        Exceptions::propagate);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<Media> callback) {
        // TODO we need to get data at key and "BEFORE" will need a getMediasEndingAtKey() method
    }

    @NonNull
    @Override
    public String getKey(@NonNull Media item) {
        return item.getPagingKey();
    }

    private Single<List<Media>> getMediasStartingAtKey(final String key, final int size) {
        // TODO left off here, this is the real "work" how do we juggle keys, and data and fetching new data

    }
}
