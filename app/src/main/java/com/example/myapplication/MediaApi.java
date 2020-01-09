package com.example.myapplication;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

class MediaApi {
    private final int pageSize = 10;
    Observable<Media> getPageOfMedias(int page, String userId) {
        // fake web request
        return Observable
                .create((ObservableOnSubscribe<Media>) emitter -> {
                    for (int i = 0; i < pageSize; i++) {
                        String idString = String.valueOf(page * i);
                        String contentString = "some content: " + userId; // TODO
                        emitter.onNext(new Media(idString, contentString));
                    }
                    emitter.onComplete();
                })
                .delay(5, TimeUnit.SECONDS);
    }
}
