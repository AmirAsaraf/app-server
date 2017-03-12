package com.app.model;

import java.io.Serializable;

/**
 * Created by aa069w on 1/12/2017.
 */
public class Image implements Serializable{
    private String url;
    private int width;
    private int height;
    private int retrievalDate;
    private String sourceName;

    public Image() {
    }

    public Image(String url, int width, int height, int retrievalDate, String sourceName) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.retrievalDate = retrievalDate;
        this.sourceName = sourceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(int retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
