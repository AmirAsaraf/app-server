package com.app.model;


/**
 * POJO to represent image info. Holds the Image metadata like width, height,
 * imageURL and imageType"
 * 
 */
public class ImageInfo {

    public static final String MANUAL = "Manual";

    private String type;
    private String url;
    private Integer width;
    private Integer height;
    private String sourceName;
    private String retrievalDate;

    public ImageInfo() {
    }

    public ImageInfo(String type, String url, Integer width, Integer height, String sourceName, String retrievalDate) {
        this.type = type;
        this.url = url;
        this.width = width;
        this.height = height;
        this.sourceName = sourceName;
        this.retrievalDate = retrievalDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getRetrievalDate() {
        return retrievalDate;
    }

    public void setRetrievalDate(String retrievalDate) {
        this.retrievalDate = retrievalDate;
    }

}
