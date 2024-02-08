package it.addon.bigdata.microservices.images.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Image {

    private Integer number;
    private String title;
    private String url;

    public Image() {
    }

    public Image(Integer number, String title, String url) {
        this.number = number;
        this.title = title;
        this.url = url;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}