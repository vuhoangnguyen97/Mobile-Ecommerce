package com.example.legia.mobileweb.DTO;

public class menuMain {
    private int idMenu;
    private String tenMenu;

    public menuMain(int idMenu, String tenMenu) {
        this.idMenu = idMenu;
        this.tenMenu = tenMenu;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    
    public String getTenMenu() {
        return tenMenu;
    }

    public void setTenMenu(String tenMenu) {
        this.tenMenu = tenMenu;
    }
}

