package com.example.legia.mobileweb.DTO;

public class chiNhanh {
    private String diaChi;
    private String urlBanDo;

    public chiNhanh(String diaChi, String urlBanDo) {
        this.diaChi = diaChi;
        this.urlBanDo = urlBanDo;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getUrlBanDo() {
        return urlBanDo;
    }

    public void setUrlBanDo(String urlBanDo) {
        this.urlBanDo = urlBanDo;
    }
}
