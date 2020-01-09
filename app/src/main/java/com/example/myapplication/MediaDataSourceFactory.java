package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

public class MediaDataSourceFactory extends DataSource.Factory<Integer, Media> {
    private final String userId;
    private final MediaApi mediaApi;

    MediaDataSourceFactory(final MediaApi mediaApi,
                           final String userId) {
        this.mediaApi = mediaApi;
        this.userId = userId;
    }


    @NonNull
    @Override
    public DataSource<Integer, Media> create() {
        return new MediaDataSource(mediaApi, userId);
    }
}
