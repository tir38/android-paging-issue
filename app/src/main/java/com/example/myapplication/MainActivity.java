package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MediaListAdapter mediaListAdapter = new MediaListAdapter();
        recyclerView.setAdapter(mediaListAdapter);

        String userId = "1234";
        Repository repository = new Repository(new MediaApi());
        disposable = repository
                .getPageListOfMedia(userId)
                .flatMapSingle(pagedList -> {
                    // delay the pagedlist emission long enough for it to be populated by other thread
                    return Observable.interval(0, 15, TimeUnit.MILLISECONDS)
                            .doOnNext(ignore -> {
                                Log.d("TAG", "Pagedlist size is: " + pagedList.size());
                            })
                            .filter(ignore -> !pagedList.isEmpty())
                            .map(ignore -> pagedList)
                            .firstOrError();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mediaListAdapter::submitList,
                        Exceptions::propagate);

        // After X seconds simulate a change in the backing data
        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(() -> {
                    Log.d("TAG", "invaliding data source");
                    mediaListAdapter.getCurrentList().getDataSource().invalidate();
                },
                MediaApi.TIME_UNTIL_LIST_UPDATES);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
