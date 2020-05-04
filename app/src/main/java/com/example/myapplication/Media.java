package com.example.myapplication;

import java.util.Objects;

public class Media {

    private final String id;
    private final String content;
    // key used for paging requests. We purposefully don't use ID
    private final String pagingKey;

    public Media(String id, String content, String pagingKey) {
        this.id = id;
        this.content = content;
        this.pagingKey = pagingKey;
    }


    public String getId() {

        return id;
    }

    public String getContent() {
        return content;
    }

    public String getPagingKey() {
        return pagingKey;
    }

    // TODO do we need to add paging key to equals/hashcode?
    /**
     * Use for are contents same
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return Objects.equals(id, media.id) &&
                Objects.equals(content, media.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }
}
