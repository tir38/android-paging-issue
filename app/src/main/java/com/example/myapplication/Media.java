package com.example.myapplication;

import java.util.Objects;

public class Media {

    private final String id;
    private final String content;

    public Media(String id, String content) {
        this.id = id;
        this.content = content;
    }


    public String getId() {

        return id;
    }

    public String getContent() {
        return content;
    }

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
