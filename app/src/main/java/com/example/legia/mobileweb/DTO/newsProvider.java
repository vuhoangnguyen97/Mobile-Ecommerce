package com.example.legia.mobileweb.DTO;

public class newsProvider {
    private int imgNewProvider;
    private String nameProvider;
    private String urlProvider;

    public newsProvider() {

    }

    public newsProvider(int imgNewProvider, String nameProvider, String urlProvider) {
        this.imgNewProvider = imgNewProvider;
        this.nameProvider = nameProvider;
        this.urlProvider = urlProvider;
    }

    public int getImgNewProvider() {
        return imgNewProvider;
    }

    public void setImgNewProvider(int imgNewProvider) {
        this.imgNewProvider = imgNewProvider;
    }

    public String getNameProvider() {
        return nameProvider;
    }

    public void setNameProvider(String nameProvider) {
        this.nameProvider = nameProvider;
    }

    public String getUrlProvider() {
        return urlProvider;
    }

    public void setUrlProvider(String urlProvider) {
        this.urlProvider = urlProvider;
    }
}
