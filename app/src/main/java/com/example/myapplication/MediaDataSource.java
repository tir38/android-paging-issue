package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.exceptions.Exceptions;

public class MediaDataSource extends PageKeyedDataSource<Integer, Media> {

    private static final int FIRST_PAGE = 0;

    private final MediaApi mediaApi;
    private final String userId;
    private Disposable getPageDisposable = Disposables.empty();

    MediaDataSource(final MediaApi mediaApi, final String userId) {
        this.mediaApi = mediaApi;
        this.userId = userId;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Media> callback) {
        Log.d("TAG", "loadInitial");
        getPageDisposable.dispose();
        getPageDisposable = getMediasForPage(FIRST_PAGE)
                .subscribe(
                        medias -> callback.onResult(medias, null, FIRST_PAGE + 1),
                        Exceptions::propagate);
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params,
                           @NonNull final LoadCallback<Integer, Media> callback) {
        Log.d("TAG", "loadBefore");
        Integer pageToLoad = params.key;

        getPageDisposable.dispose();
        getPageDisposable = getMediasForPage(pageToLoad)
                .subscribe(
                        medias -> {
                            //if there is a previous page the decrement, or if desired page = 1 then there is no
                            // previous page so set adjacent as null
                            Integer adjacentPage = (pageToLoad > FIRST_PAGE) ? pageToLoad - 1 : null;
                            callback.onResult(medias, adjacentPage);
                        },
                        Exceptions::propagate);
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params,
                          @NonNull final LoadCallback<Integer, Media> callback) {

        Log.d("TAG", "loadAfter");
        Integer pageToLoad = params.key;

        getPageDisposable.dispose();
        getPageDisposable = getMediasForPage(pageToLoad)
                .subscribe(
                        medias -> {
                            // TODO how do we know from API if there are more items?
                            // if there is a next page the increment, or if desired page = 1 then there is no next
                            // page so set adjacent as null
                            boolean hasMore = true;

                            Integer adjacentPage = hasMore ? pageToLoad + 1 : null;
                            callback.onResult(medias, adjacentPage);
                        },
                        Exceptions::propagate);
    }

    private Single<List<Media>> getMediasForPage(final int page) {
        return mediaApi.getPageOfMedias(page, userId)
                .toList();
    }
}
