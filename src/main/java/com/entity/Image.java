package com.entity;

public class Image {
    private String url;
    private String altText;

    public Image(String url, String altText) {
        this.url = url;
        this.altText = altText;
    }
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getAltText() {
        return altText;
    }
    public void setAltText(String altText) {
        this.altText = altText;
    }
    
}
