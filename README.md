##Issue clearing view when invalidating PagedListAdapter

Start with conventional paging library setup with `PagedListAdapter`, `DataSource`, `DataSourceFactory`, and `RecyclerView` all [per the docs](https://developer.android.com/topic/libraries/architecture/paging)

The list loads data just fine. I then am notified that a new item has been added to the backing data so I call [`invalidate`](https://developer.android.com/reference/androidx/paging/DataSource?hl=en#invalidate()) on the `DataSource`.

However I momentarily see the entire list clears as new data is loaded.

You can see this in example.mp4. The list initially loads 10 items per page. Then when the "invalidate" is trigged, the whole list clears. Only after fetching new items does the "new" data appear.

![example video](example.mp4)

The biggest problem is that it's causing the Adapter to re-bind each of my `ViewHolders` which (not shown in sample code) is triggering a lot of wasted work.
