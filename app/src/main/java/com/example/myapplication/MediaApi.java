package com.example.myapplication;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

class MediaApi {

    static final int TIME_UNTIL_LIST_UPDATES = 10000;
    static final int FAKE_REQUEST_DELAY = 2000;

    private static final int SERVER_SIDE_PAGE_SIZE = 20;

    private long initialRequestTime = System.currentTimeMillis();

    Observable<Media> getPageOfMedias(int page, String userId) {
        // fake web request

        // during the first X seconds return the "original" data
        long currentTime = System.currentTimeMillis();
        Observable<Media> mediaObservable;
        if (currentTime - initialRequestTime < TIME_UNTIL_LIST_UPDATES) {
            mediaObservable = Observable
                    .create((ObservableOnSubscribe<Media>) emitter -> {
                        for (int i = 0; i < SERVER_SIDE_PAGE_SIZE; i++) {
                            int id = (page * SERVER_SIDE_PAGE_SIZE) + i;
                            String contentString = "original content for user: " + userId;
                            emitter.onNext(new Media(String.valueOf(id), contentString));
                        }
                        emitter.onComplete();
                    })
                    .doOnSubscribe(disposable -> {
                        if (initialRequestTime == -1) {
                            initialRequestTime = System.currentTimeMillis();
                        }
                    });

        } else {
            // after X seconds, return "new" data, starting with a single page of new media
            // (noted by negative numbers), followed by pages of "old data" starting at 0 id.
            mediaObservable = Observable
                    .create(emitter -> {
                        for (int i = 0; i < SERVER_SIDE_PAGE_SIZE; i++) {
                            int id = (page * SERVER_SIDE_PAGE_SIZE) + i - 10;
                            String contentString;
                            if (id < 0) {
                                contentString = "new content for user: " + userId;
                            } else {
                                contentString = "original content for user: " + userId;
                            }

                            emitter.onNext(new Media(String.valueOf(id), contentString));
                        }
                        emitter.onComplete();
                    });
        }

        return mediaObservable.delay(FAKE_REQUEST_DELAY, TimeUnit.MILLISECONDS);
    }



}
